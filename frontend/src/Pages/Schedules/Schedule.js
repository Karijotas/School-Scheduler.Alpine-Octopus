import { addClass, closest, L10n, remove, setCulture } from '@syncfusion/ej2-base';
import { ButtonComponent, CheckBoxComponent } from '@syncfusion/ej2-react-buttons';
import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';
import { TreeViewComponent } from '@syncfusion/ej2-react-navigations';
import { Agenda, Timezone, Day, DragAndDrop, ExcelExport, Inject, Month, Print, Resize, ResourceDirective, ResourcesDirective, Schedule, ScheduleComponent, ViewDirective, ViewsDirective, Week, WorkWeek } from '@syncfusion/ej2-react-schedule';
import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { Grid, Header, List, Message, Segment } from "semantic-ui-react";
import "../../../node_modules/@syncfusion/ej2-icons/styles/bootstrap5.css";
import { updateSampleSection } from "./sample-base";
import "./Schedule.css";
import { MessagePopUp } from "./Message";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

// if (Browser.isIE) {
//   Timezone.prototype.offset = (date, timezone) => {
//     return tz.zone(timezone).utcOffset(date.getTime());
//   };
// } 
L10n.load({
  // 'en-US': {
  //   'schedule': {
  //     'saveButton': 'Add',
  //     'cancelButton': 'Close',
  //     'deleteButton': 'Remove',
  //     'newEvent': 'Add Event',
  //   },

  lt: {
    schedule: {
      saveButton: "Išsaugoti",
      cancelButton: "Uždaryti",
      deleteButton: "Pašalinti",
      newEvent: "Pridėti pamoką",
      editEvent: "Redaguoti pamoką",
    },
    timeFormats: {
      short: "HH",
    },
  },
}
);
setCulture("lt");

Schedule.Inject(
  Day,
  Week,
  WorkWeek,
  Month,
  Agenda,
  Resize,
  DragAndDrop,
  Print,
  ExcelExport
);

export function ScheduleView() {
  let scheduleObj;
  const params = useParams();
  const [schedules, setSchedules] = useState([]);
  const [lessons, setLessons] = useState([]);
  const [holidays, setHolidays] = useState([]);
  const [error, setError] = useState(false)
  const [subjects, setSubjects] = useState([]);
  const [subjectId, setSubjectId] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [active, setActive] = useState(false);
  const [online, setOnline] = useState(false);
  const [rooms, setRooms] = useState([]);
  const [teachers, setTeachers] = useState([]);
  const [room, setRoom] = useState({
    id: "",
    name: ""
  });
  const [teacher, setTeacher] = useState({
    id: "",
    name: ""
  });
  const [subject, setSubject] = useState("");
  const [okey, setOkey] = useState("");
  const [lesson, setLesson] = useState({
    id: "",
    name: "",
    startTime: "",
    endTime: "",
  });
  const [statusMessage, setStatusMessage] = useState("");
  const [updated, setUpdated] = useState();

  let treeObj;
  let isTreeItemDropped = false;
  let draggedItemId = "";
  const allowDragAndDrops = true;
  let isDialogButtonsSet = false;
  let flag = true;
  const timezone = new Timezone();

  useEffect(() => {
    updateSampleSection();
  }, []);


  function treeTemplate(props) {
    return (
      <div id="waiting">
        <div id="waitdetails">
          <div id="waitlist">{props.Subject}</div>
          <div id="waitcategory">{props.LessonHours} val </div>
        </div>
      </div>
    );
  }

  function onItemSelecting(args) {
    args.cancel = true;
  }

  function onTreeDrag(event) {
    if (scheduleObj.isAdaptive) {
      let classElement = scheduleObj.element.querySelector(".e-device-hover");
      if (classElement) {
        classElement.classList.remove("e-device-hover");
      }
      if (event.target.classList.contains("e-work-cells")) {
        addClass([event.target], "e-device-hover");
      }
    }
  }
  function onActionBegin(event) {
    console.log(event);
    if (event.requestType === "eventCreate" && isTreeItemDropped) {
      let treeViewData = treeObj.fields.dataSource;
      const filteredPeople = treeViewData.filter(
        (item) => item.Id !== parseInt(draggedItemId, 10)
      );
      treeObj.fields.dataSource = filteredPeople;
      let elements = document.querySelectorAll(
        ".e-drag-item.treeview-external-drag"
      );
      for (let i = 0; i < elements.length; i++) {
        remove(elements[i]);
      }
    }
    else if (event.requestType === 'eventCreate') {
      //  console.log("cia kurimas");
      console.log(event);
      var newLesson = ({
        id: event.data[0].Id,
        startTime: timezone.removeLocalOffset(new Date(event.data[0].StartTime)).toISOString(),
        endTime: timezone.removeLocalOffset(new Date(event.data[0].EndTime)).toISOString()
      })
      console.log(newLesson);
      createLessonOnSchedule(newLesson);

    }
    else if (event.requestType === 'eventRemove') {
      removeLessonOnSchedule(event.data[0])

    }
    else if (event.requestType === 'eventChanged') {
      console.log("cia changas")
      //console.log(event.data)

    }
    else if (event.action == "update" || (event.action == "batch" && event.changed != null)) {
      //... custom action goes here 
      console.log("cia editas")
    }
    else if (event.requestType === 'eventClose') {
      setLesson({});

    }
    // console.log(event);
  }

  const onPopupOpen = (event) => {
    setSubject("");
    setOnline("");
    setRoom({});
    setTeacher({});
    setLesson({});
    if (event.type === 'Editor' && event.data.Subject === undefined) {
      console.log("cia naujas");

      setStartTime(timezone.removeLocalOffset(new Date(event.data.StartTime)).toISOString());
      setEndTime(timezone.removeLocalOffset(new Date(event.data.EndTime)).toISOString());
      var editedLesson = ({
        id: event.data.Id,
        name: event.data.Subject,
        online: event.data.Online,
        room: event.data?.Room,
        teacher: event.data?.Teacher,
        startTime: timezone.removeLocalOffset(new Date(event.data.StartTime)).toISOString(),
        endTime: timezone.removeLocalOffset(new Date(event.data.EndTime)).toISOString()
      })
    } else if (event.type === 'Editor') {
      console.log("cia editinimas");
      console.log(event)
      setLesson({ name: event.data.Subject, id: event.data.Id });
      setOnline(event.data.Online);
      setRoom({ name: event.data?.Room });
      setTeacher({ name: event.data?.Teacher });
      setStartTime(timezone.removeLocalOffset(new Date(event.data.StartTime)).toISOString());
      setEndTime(timezone.removeLocalOffset(new Date(event.data.EndTime)).toISOString());
      //setSubjectId(event.data.SubjectId)
      var editedLesson = ({
        id: event.data.Id,
        name: event.data.Subject,
        online: event.data.Online,
        room: event.data?.Room,
        teacher: event.data?.Teacher,
        startTime: timezone.removeLocalOffset(new Date(event.data.StartTime)).toISOString(),
        endTime: timezone.removeLocalOffset(new Date(event.data.EndTime)).toISOString()
      })
      console.log(editedLesson);
    }
  };

  function onTreeDragStop(event) {
    let treeElement = closest(event.target, ".e-treeview");
    console.log(event);
    if (!treeElement) {
      event.cancel = true;
      let scheduleElement = closest(event.target, ".e-content-wrap");
      if (scheduleElement) {
        let treeviewData = treeObj.fields.dataSource;
        if (event.target.classList.contains("e-work-cells")) {
          const filteredData = treeviewData.filter(
            (item) => item.Id === parseInt(event.draggedNodeData.id, 10)
          );
          let cellData = scheduleObj.getCellDetails(event.target);
          let eventData = {
            Id: filteredData[0].Id,
            Subject: filteredData[0].Subject,
            Teacher: filteredData[0].Teacher,
            Room: filteredData[0].Room,
            StartTime: cellData.startTime,
            EndTime: cellData.endTime,
          };
          setTeacher({ name: filteredData[0].Teacher, id: filteredData[0].Id });
          setRoom({ name: filteredData[0].Room, id: filteredData[0].Id });
          setLesson({ name: filteredData[0].Subject, id: filteredData[0].Id });
          setStartTime(cellData.startTime);
          setEndTime(cellData.endTime);
          console.log(filteredData[0]);
          scheduleObj.openEditor(eventData, 'Add', true);
          isTreeItemDropped = true;
          draggedItemId = event.draggedNodeData.id;

        }
      }
    }
    document.body.classList.remove("e-disble-not-allowed");
  }
  function onTreeDragStart() {
    document.body.classList.add("e-disble-not-allowed");
  }
  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);


  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/lessons`)
      .then((response) => response.json())
      // .then((jsonRespones) => setLessons(jsonRespones))
      .then(setLessons)
      .then(setActive(false));
  }, [params, active]);

  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjects);
  }, [params, active]);

  useEffect(() => {
    fetch(`/api/v1/holidays/`)
      .then((response) => response.json())
      .then(setHolidays);
  }, [params, active]);

  const createLessonOnSchedule = (props) => {
    fetch(
      `/api/v1/schedule/${params.id}/create/${lesson.id}/${props.startTime}/${props.endTime}/${online ? `?givenBoolean=${online}` : " "}${room.id ? `&roomId=${room.id}` : " "}${teacher.id ? `&teacherId=${teacher.id}` : " "}`, {
      method: 'PATCH'
    })
      .then(setActive(true))
      .then(applyResult);
    setLesson({});
    setStartTime("");
    setEndTime("");
  };

  const applyResult = (result) => {
    if (result.ok) {
      setActive(true);
      setLesson({});
      setStartTime("");
      setEndTime("");
    } else {
      setActive(true);
      setError(true);
      let info = result.json()
        .then((jsonResponse) => setOkey(jsonResponse.message));

      ;
      setTimeout(() => {
        setOkey("");
        setError(false);
      }, 10000)
        ;
    }
  };

  const removeLessonOnSchedule = (props) => {
    fetch(
      `/api/v1/schedule/${params.id}/remove/${props.Id}`, {
      method: 'DELETE'
    })
      .then(setActive(true));
  }

  useEffect(() => {
    fetch("/api/v1/schedule/" + params.id)
      .then((response) => response.json())
      .then(setSchedules);
  }, [params]);

  function onPrint() {
    scheduleObj.print();
  }

  function onExportClick() {
    scheduleObj.exportToExcel();
  }
  const lessonsOnSchedule = (lessons).map((l) => {
    return {
      Id: l.id,
      Subject: l.subject.name,
      SubjectId: l.subject.id,
      StartTime: l.startTime,
      EndTime: l.endTime,
      Room: l.room?.name,
      Teacher: l.teacher?.name,
      GroupId: l.subject.id,
      Online: l.online,
      Description: l.online ? "ONLINE" : "",
      StatusMessage: l.statusMessage,
      Status: l.status
    };
  });
  // {console.log(lessons)}
  const subjectsOnSchedule = subjects.map((s) => {
    return {
      Id: s.id,
      Subject: s.subject.name,
      SubjectId: s.subject.id,
      StartTime: s.startTime,
      EndTime: s.endTime,
      Room: s.room?.name,
      Teacher: s.teacher?.name,
      GroupId: s.subject.id,
      LessonHours: s.lessonHours,
      Rooms: s.subject.subjectRooms,
      Teachers: s.subjectTeachers
    };
  });

  const holidaysOnSchedule = holidays.map((h) => {
    return {
      Id: h.id,
      Subject: h.name,
      StartTime: h.startDate,
      EndTime: h.endDate,
      IsBlock: true,
      // AllDay: true,
    };
  });

  var filteredMessages = lessons.filter((l) => l.status !== 0);

  function ClickButton() {
    scheduleObj.closeEditor();
  }

  const subjectFields = {
    dataSource: subjectsOnSchedule,
    text: "Subject",
    id: "Id",
  };
  const roomFields = {
    dataSource: subjectsOnSchedule,
    text: "Room",
    id: "Id",
  };
  const teacherFields = {
    dataSource: subjectsOnSchedule,
    text: "Teacher",
    id: "Id",
  };
  function eventTemplate(props) {
    return (<div className="template-wrap" style={{ background: props.SecondaryColor }}>
      <div className="subject" style={{ background: props.GroupId }}>{props.Subject} {props.Status === 0 ? "" : <span className="e-icons e-warning"></span>}</div>
      <div className="event-description">{props.Description}</div>
      {/* {console.log(props)} */}
    </div>);
  }

  function editorTemplate(props) {
    return props !== undefined ? (<table className="custom-event-editor" style={{ width: '100%' }}>
      <tbody>
        <tr><td className="e-textlabel">Pamoka: </td><td colSpan={4}>
          <DropDownListComponent
            id="Subject"
            placeholder='Pasirinkti'
            data-name="Subject"
            className="e-field"
            style={{ width: '100%' }}
            value={lesson.name === "" ? props.Subject : lesson.name || props.Subject === undefined ? lesson.name : props.Subject}
            dataSource={subjectsOnSchedule}
            fields={subjectFields}
            onChange={(e) => setLesson({ id: e.target.itemData.Id, name: e.value })}
          >
          </DropDownListComponent>
          {/* {console.log(props)}
          {console.log(subjects)} */}
        </td></tr>
        <br />
        <tr><td className="e-textlabel">Nuotolinė pamoka: </td><td colSpan={4}>
          <CheckBoxComponent name="Online" value={online} checked={props.Online || online} onChange={(e) => setOnline(e.target.properties.checked)}></CheckBoxComponent>
        </td></tr>
        {/* {console.log(online)} */}
        <br />
        {!online || props.Online ? (props.Room === undefined ? <tr><td className="e-textlabel">Kabinetas: </td><td colSpan={4}>

          <DropDownListComponent id="Room" value={props.Room || room.name} placeholder='Pasirinkti' data-name="Room" className="e-field" style={{ width: '100%' }} dataSource={subjectsOnSchedule}
            fields={roomFields} onChange={(e) => setRoom({ id: e.target.itemData.Id, name: e.value })}>
          </DropDownListComponent >
        </td></tr> : <tr><td className="e-textlabel">Kabinetas:</td><td colSpan={4}> <br />
          <div>{props.Room}<br /></div>
          <br />
        </td></tr>
        ) : ""}
        {props.Teacher === undefined ? <tr><td className="e-textlabel">Mokytojas: </td><td colSpan={4}>
          <DropDownListComponent id="Teacher" value={props.Teacher || teacher.name} placeholder='Pasirinkti' data-name="Teacher" className="e-field" style={{ width: '100%' }} dataSource={subjectsOnSchedule}
            fields={teacherFields} onChange={(e) => setTeacher({ id: e.target.itemData.Id, name: e.value })}>
          </DropDownListComponent>
        </td></tr> : <tr><td className="e-textlabel">Mokytojas: </td><td colSpan={4}>
          <div>{props.Teacher}</div>
        </td></tr>}

        <tr><td className="e-textlabel">Data nuo: </td><td colSpan={4}>
          <DateTimePickerComponent firstDayOfWeek={1} format='yyyy/MM/dd HH' timeFormat={"HH"} step={60} locale='lt' id="StartTime" data-name="StartTime" value={new Date(props.startTime || props.StartTime || startTime)} onChange={(e) => setStartTime(new Date(e.value))} className="e-field"></DateTimePickerComponent>
        </td></tr>
        <tr><td className="e-textlabel">Data iki: </td><td colSpan={4}>
          <DateTimePickerComponent firstDayOfWeek={1} locale='lt' format='yyyy/MM/dd HH' timeFormat={"HH"} step={60} id="EndTime" data-name="EndTime" value={new Date(props.endTime || props.EndTime || endTime)} onChange={(e) => setEndTime(new Date(e.value))} className="e-field"></DateTimePickerComponent>
        </td></tr>
        {/* <tr><td className="e-textlabel">Pamoka nuo</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']}>
          </DropDownListComponent>
        </td></tr>
        <tr><td className="e-textlabel">Pamoka iki</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']}>
          </DropDownListComponent>
        </td></tr> */}
        <tr>
          <td className="e-textlabel">Pastabos: </td>
          <td colSpan={4}>
            <textarea
              id="Description"
              className="e-field e-input"
              name="Description"
              rows={3}
              cols={50}
              style={{
                width: "100%",
                height: "60px !important",
                resize: "vertical",
              }}
            ></textarea>
          </td>
        </tr>
      </tbody>
    </table>
    ) : (
      ""
    );
  }

  const data = [
    {
      Id: 1,
      Subject: "Velykų atostogos",
      StartTime: new Date(2023, 1, 10, 24, 0),
      EndTime: new Date(2023, 1, 24, 24, 0),
      IsAllDay: true,
      IsBlock: true,
      CategoryColor: "#357cd2",
      GroupId: 2,
    },
    {
      Id: 2,
      Subject: "Not Available",
      StartTime: new Date(2023, 1, 6, 10, 0),
      EndTime: new Date(2023, 1, 6, 12, 0),
      IsAllDay: false,
      IsBlock: true,
    },
  ];

  const resourceData = [
    { GroupText: schedules.name, GroupId: 1, GroupColor: "#1c5252" },
    { GroupText: schedules.name, GroupId: 2, GroupColor: "#2a787a" },
    { GroupText: schedules.name, GroupId: 3, GroupColor: "#569a9b" },
    { GroupText: schedules.name, GroupId: 4, GroupColor: "#40b3b6" },
    { GroupText: schedules.name, GroupId: 5, GroupColor: "#73cdce" },
    { GroupText: schedules.name, GroupId: 6, GroupColor: "#a8e0e1" },
    { GroupText: schedules.name, GroupId: 7, GroupColor: "#c3e3e4" },
    { GroupText: schedules.name, GroupId: 8, GroupColor: "#c6dcbe" },
    { GroupText: schedules.name, GroupId: 9, GroupColor: "#9fc592" },
    { GroupText: schedules.name, GroupId: 10, GroupColor: "#80b56e" },
    { GroupText: schedules.name, GroupId: 11, GroupColor: "#559640" },
    { GroupText: schedules.name, GroupId: 12, GroupColor: "#428b27" },
    { GroupText: schedules.name, GroupId: 13, GroupColor: "#307318" },
    { GroupText: schedules.name, GroupId: 14, GroupColor: "#285e15" },
    { GroupText: schedules.name, GroupId: 15, GroupColor: "#4d5e15" },
    { GroupText: schedules.name, GroupId: 16, GroupColor: "#7a7a13" },
    { GroupText: schedules.name, GroupId: 17, GroupColor: "#949413" },
    { GroupText: schedules.name, GroupId: 18, GroupColor: "#b3b30f" },
    { GroupText: schedules.name, GroupId: 19, GroupColor: "#e7e707" },
    { GroupText: schedules.name, GroupId: 20, GroupColor: "#ffe016" },
    { GroupText: schedules.name, GroupId: 21, GroupColor: "#ffcd16" },
  ];

  const dataaSource = [...lessonsOnSchedule, ...holidaysOnSchedule];

  return (

    <div>
      {error && <Message negative>
        <Message.Header>Nepavyko sukurti</Message.Header>
        <p >
          {okey}
        </p>
      </Message>}
      <div className="schedule-control-section">
        <div className="control-section">
          <div className="control-wrapper drag-sample-wrapper">
            <Grid>
              <Grid.Row>
                <Grid.Column width={13}>
                  <div className="control-panel calendar-export">
                    <ButtonComponent
                      id="printBtn"
                      cssClass="title-bar-btn"
                      iconCss="e-icons e-print"
                      onClick={onPrint.bind(this)}
                      content="Print"
                    />
                  </div>
                  <h1 className="title-text">{schedules.name}</h1>



                  <ScheduleComponent
                    id='schedule-drag-drop'
                    cssClass='schedule-drag-drop'
                    ref={schedule => scheduleObj = schedule}
                    timeFormat='HH'
                    firstDayOfWeek='1'
                    height='550px'
                    timezone='Europe/Vilnius'
                    editorTemplate={editorTemplate}
                    selectedDate={new Date(2023, 1, 10, 24, 0)}
                    eventSettings={{
                      dataSource: lessonsOnSchedule,
                      fields: {
                        editorTemplate
                      }

                    }}
                    colorField='Color'
                    currentView='Month'
                    actionBegin={onActionBegin.bind(this)}
                    popupOpen={onPopupOpen}>
                    {/* popupOpen={onPopupOpen.bind(this)}> */}
                    {/* {console.log(lessons)}
                  <ScheduleComponent
                    id="schedule-drag-drop"
                    cssClass="schedule-drag-drop"
                    ref={(schedule) => (scheduleObj = schedule)}
                    timeFormat="HH"
                    firstDayOfWeek="1"
                    height="550px"
                    timezone="Europe/Vilnius"
                    editorTemplate={editorTemplate}
                    selectedDate={new Date(2023, 1, 10, 24, 0)}
                    eventSettings={{
                      dataSource: dataaSource,
                      fields: {
                        editorTemplate,
                      },
                    }}

                    colorField="Color"
                    currentView="Month"
                    actionBegin={onActionBegin.bind(this)}
                  >
                    {/* popupOpen={onPopupOpen.bind(this)}> */}
                    {/* {console.log(lessons)}
                  {console.log(endTime)} */}
                    <ResourcesDirective>
                      <ResourceDirective field='GroupId' title='Owner' name='Owners' dataSource={resourceData} textField='GroupText' idField='GroupId' colorField='GroupColor'>
                      </ResourceDirective>
                    </ResourcesDirective>
                    <ViewsDirective>
                      <ViewDirective option='Day' startHour='01:00' endHour='15:00' timeScale={{ interval: 1, slotCount: 1 }} eventTemplate={eventTemplate.bind(this)} />
                      <ViewDirective option='Week' startHour='01:00' endHour='15:00' timeScale={{ slotCount: 1 }} eventTemplate={eventTemplate.bind(this)} />
                      <ViewDirective option='WorkWeek' startHour='01:00' endHour='15:00' timeScale={{ slotCount: 1 }} eventTemplate={eventTemplate.bind(this)} />
                      <ViewDirective option='Month' startHour='01:00' endHour='15:00' timeScale={{ slotCount: 1 }} eventTemplate={eventTemplate.bind(this)} />
                    </ViewsDirective>
                    <Inject services={[Day, Week, WorkWeek, Month, Agenda, DragAndDrop, ExcelExport, DragAndDrop]} />
                    {/* {console.log(subjectId)} */}

                  </ScheduleComponent></Grid.Column>
                <Grid.Column width={3}>

                  <Segment compact id="treeview" > <Header textAlign='center'>Nesuplanuotos Pamokos</Header>
                    <TreeViewComponent ref={tree => treeObj = tree}

                      cssClass='treeview-external-drag'
                      dragArea=".drag-sample-wrapper"
                      nodeTemplate={treeTemplate.bind(this)}
                      fields={{ dataSource: subjectsOnSchedule, text: 'Subject', id: 'Id', }}
                      nodeDragStop={onTreeDragStop.bind(this)}
                      nodeSelecting={onItemSelecting.bind(this)}
                      nodeDragging={onTreeDrag.bind(this)}
                      nodeDragStart={onTreeDragStart.bind(this)}
                      allowDragAndDrop={allowDragAndDrops} />
                  </Segment>
                  <Segment>
                    <List compact id="treeview">
                      <Header textAlign="center">Validacijos</Header>
                      {filteredMessages.map((lesson) => (
                        <div key={lesson.id}>
                          <Segment><List.Item >{lesson.statusMessage}</List.Item></Segment>
                        </div>
                      ))}
                    </List>
                  </Segment>
                </Grid.Column>
              </Grid.Row>
            </Grid>
          </div>
        </div>
      </div>
    </div>
  );
}