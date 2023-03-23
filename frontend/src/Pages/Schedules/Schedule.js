import { addClass, closest, L10n, remove } from '@syncfusion/ej2-base';
import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';
import { TreeViewComponent } from '@syncfusion/ej2-react-navigations';
import { Agenda, Day, DragAndDrop, ExcelExport, Inject, Month, Print, Resize, ResourceDirective, ResourcesDirective, Schedule, ScheduleComponent, ViewDirective, ViewsDirective, Week, WorkWeek } from '@syncfusion/ej2-react-schedule';
import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { Button, Grid, Header, Message, Segment } from "semantic-ui-react";
import "../../../node_modules/@syncfusion/ej2-icons/styles/bootstrap5.css";
import { updateSampleSection } from './sample-base';
import './Schedule.css';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

L10n.load({
  'en-US': {
    'schedule': {
      'saveButton': 'Add',
      'cancelButton': 'Close',
      'deleteButton': 'Remove',
      'newEvent': 'Add Event',
    },
  }
});

Schedule.Inject(Day, Week, WorkWeek, Month, Agenda, Resize, DragAndDrop, Print, ExcelExport, DragAndDrop);

export function ScheduleView() {
  let scheduleObj;
  const params = useParams();


  const [schedules, setSchedules] = useState([]);
  const [lessons, setLessons] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [subjectId, setSubjectId] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [active, setActive] = useState(false);

  const [updated, setUpdated] = useState();

  let treeObj;
  let isTreeItemDropped = false;
  let draggedItemId = '';
  const allowDragAndDrops = true;
  useEffect(() => {
    updateSampleSection();
  }, []);

  function treeTemplate(props) {
    return (<div id="waiting"><div id="waitdetails"><div id="waitlist">{props.Subject}</div>
      <div id="waitcategory">{props.LessonHours} val </div></div></div>);
  }

  function onItemSelecting(args) {
    args.cancel = true;
  }

  function onTreeDrag(event) {
    if (scheduleObj.isAdaptive) {
      let classElement = scheduleObj.element.querySelector('.e-device-hover');
      if (classElement) {
        classElement.classList.remove('e-device-hover');
      }
      if (event.target.classList.contains('e-work-cells')) {
        addClass([event.target], 'e-device-hover');
      }
    }
  }
  function onActionBegin(event) {
    if (event.requestType === 'eventCreate' && isTreeItemDropped) {
      let treeViewData = treeObj.fields.dataSource;
      const filteredPeople = treeViewData.filter((item) => item.Id !== parseInt(draggedItemId, 10));
      treeObj.fields.dataSource = filteredPeople;
      let elements = document.querySelectorAll('.e-drag-item.treeview-external-drag');
      for (let i = 0; i < elements.length; i++) {
        remove(elements[i]);
      }
    }
  }

  function onTreeDragStop(event) {
    let treeElement = closest(event.target, '.e-treeview');

    if (!treeElement) {
      event.cancel = true;
      let scheduleElement = closest(event.target, '.e-content-wrap');
      if (scheduleElement) {
        let treeviewData = treeObj.fields.dataSource;
        if (event.target.classList.contains('e-work-cells')) {
          const filteredData = treeviewData.filter((item) => item.Id === parseInt(event.draggedNodeData.id, 10));
          let cellData = scheduleObj.getCellDetails(event.target);
          let eventData = {
            Id: filteredData[0].Id,
            Subject: filteredData[0].Subject,
            StartTime: cellData.startTime,
            EndTime: cellData.endTime,

          };
          scheduleObj.openEditor(eventData, 'Add', true);
          isTreeItemDropped = true;
          draggedItemId = event.draggedNodeData.id;
          setSubjectId(eventData.Id);
        }
      }
    }
    document.body.classList.remove('e-disble-not-allowed');
  }
  function onTreeDragStart() {
    document.body.classList.add('e-disble-not-allowed');
  }
  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);


  function onPrintIconClick() {
    scheduleObj.print();
  }

  function onExportClick() {
    scheduleObj.exportToExcel();
  }

  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/lessons`)
      .then((response) => response.json())
      // .then((jsonRespones) => setLessons(jsonRespones))
      .then(setLessons)
      .then(setActive(false))
  }, [params, active]);

  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjects)
  }, [params, active]);

  function newStartTime() {
    const data1 = new Date(startTime).toISOString();
    setStartTime(data1);
    const data2 = new Date(endTime).toISOString();
    setEndTime(data2);
  }


  const createLessonOnSchedule = () => {
    fetch(
      `/api/v1/schedule/${params.id}/create/${subjectId}/${startTime}/${endTime}`, {
      method: 'PATCH'
    })
      .then(setActive(true));
  }

  useEffect(() => {
    fetch("/api/v1/schedule/" + params.id)
      .then((response) => response.json())
      .then(setSchedules);
  }, [params]);





  const lessonsOnSchedule = lessons.map(l => {
    return {
      Id: l.id,
      Subject: l.subject.name,
      StartTime: l.startTime,
      EndTime: l.endTime,
      GroupId: l.subject.id,
      Description: l.online ? "ONLINE" : ""
    }
  });

  const subjectsOnSchedule = subjects.map(s => {
    return {
      Id: s.id,
      Subject: s.subject.name,
      StartTime: s.startTime,
      EndTime: s.endTime,
      GroupId: s.subject.id,
      LessonHours: s.lessonHours
    }
  });

  function ClickButton() {
    scheduleObj.closeEditor();
  }

  const subjectFields = { dataSource: subjectsOnSchedule, text: 'Subject', id: 'Id', };

  function eventTemplate(props) {
    return (<div className="template-wrap" style={{ background: props.SecondaryColor }}>
      <div className="subject" style={{ background: props.GroupId }}>{props.Subject}</div>
      {console.log(props.Subject)}
      <div className="event-description">{props.Description}</div>
    </div>);
  }

  function editorTemplate(props) {
    return (props !== undefined ? <table className="custom-event-editor" style={{ width: '100%' }}>
      <tbody>
        <tr><td className="e-textlabel">Pamoka</td><td colSpan={4}>
          <DropDownListComponent
            id="Subject"
            placeholder='Pasirinkti'
            data-name="Subject"
            className="e-field"
            style={{ width: '100%' }}
            value={props.Subject}
            dataSource={subjectsOnSchedule}
            fields={subjectFields}
            onChange={(e) => setSubjectId(e.value)}>
          </DropDownListComponent>

        </td></tr>
        {/* <tr><td className="e-textlabel">Nuotolinė pamoka</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['Taip', 'Ne']}>
          </DropDownListComponent>
        </td></tr>
        <tr><td className="e-textlabel">Mokytojas</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['Mokytojas1', 'Mokytojas2', 'Mokytojas']}>
          </DropDownListComponent>
        </td></tr>
        <tr><td className="e-textlabel">Kabinetas</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['Kabinetas1', 'Kabinetas2']}>
          </DropDownListComponent>
        </td></tr> */}
        <tr><td className="e-textlabel">Data nuo</td><td colSpan={4}>
          <DateTimePickerComponent firstDayOfWeek={1} format='yyyy/MM/dd HH' timeFormat={"HH"} step={60} locale='lt' id="StartTime" data-name="StartTime" value={new Date(props.startTime || props.StartTime || startTime)} onChange={(e) => setStartTime(new Date(e.value).toISOString())} className="e-field"></DateTimePickerComponent>
        </td></tr>
        <tr><td className="e-textlabel">Data iki</td><td colSpan={4}>
          <DateTimePickerComponent firstDayOfWeek={1} locale='lt' format='yyyy/MM/dd HH' timeFormat={"HH"} step={60} id="EndTime" data-name="EndTime" value={new Date(props.endTime || props.EndTime || endTime)} onChange={(e) => setEndTime(new Date(e.value).toISOString())} className="e-field"></DateTimePickerComponent>
        </td></tr>
        {/* <tr><td className="e-textlabel">Pamoka nuo</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']}>
          </DropDownListComponent>
        </td></tr>
        <tr><td className="e-textlabel">Pamoka iki</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']}>
          </DropDownListComponent>
        </td></tr> */}
        <tr><td className="e-textlabel">Pastabos</td><td colSpan={4}>
          <textarea id="Description" className="e-field e-input" name="Description" rows={3} cols={50} style={{ width: '100%', height: '60px !important', resize: 'vertical' }}></textarea>
        </td></tr>
      </tbody>
    </table> : '');
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
      GroupId: 2


    },
    {
      Id: 2,
      Subject: "Not Available",
      StartTime: new Date(2023, 1, 6, 10, 0),
      EndTime: new Date(2023, 1, 6, 12, 0),
      IsAllDay: false,
      IsBlock: true,

    }];

  const resourceData = [
    { GroupText: schedules.name, GroupId: 1, GroupColor: '#2a787a' },
    { GroupText: schedules.name, GroupId: 2, GroupColor: '#1c5252' },
    { GroupText: schedules.name, GroupId: 3, GroupColor: '#569a9b' },
    { GroupText: schedules.name, GroupId: 4, GroupColor: '#40b3b6' },
    { GroupText: schedules.name, GroupId: 5, GroupColor: '#73cdce' },
    { GroupText: schedules.name, GroupId: 6, GroupColor: '#a8e0e1' },
    { GroupText: schedules.name, GroupId: 7, GroupColor: '#c3e3e4' },
    { GroupText: schedules.name, GroupId: 8, GroupColor: '#deebec' },
    { GroupText: schedules.name, GroupId: 9, GroupColor: '#e4e4e4' },
    { GroupText: schedules.name, GroupId: 10, GroupColor: '#cfcccc' },
    { GroupText: schedules.name, GroupId: 11, GroupColor: '#b5b4b4' },
    { GroupText: schedules.name, GroupId: 12, GroupColor: '#9d968d' },
    { GroupText: schedules.name, GroupId: 13, GroupColor: '#8f7c64' },
    { GroupText: schedules.name, GroupId: 14, GroupColor: '#866947' },
    { GroupText: schedules.name, GroupId: 15, GroupColor: '#8a5c2b' },
    { GroupText: schedules.name, GroupId: 16, GroupColor: '#9e580e' },
    { GroupText: schedules.name, GroupId: 17, GroupColor: '#c56907' },
    { GroupText: schedules.name, GroupId: 18, GroupColor: '#e17604' },
    { GroupText: schedules.name, GroupId: 19, GroupColor: '#fd901b' },
    { GroupText: schedules.name, GroupId: 20, GroupColor: '#faa952' },
    { GroupText: schedules.name, GroupId: 21, GroupColor: '#f7c48e' },
  ];

  return (
    <div >
      <div className='schedule-control-section'>
        <div className='control-section'>
          <div className='control-wrapper drag-sample-wrapper'>

            <Grid>
              <Grid.Column width={13}>

                <h1 className="title-text">{schedules.name}</h1>



                <ScheduleComponent
                  id='schedule-drag-drop'
                  cssClass='schedule-drag-drop'
                  ref={shedule => scheduleObj = shedule}
                  timeFormat='HH'
                  firstDayOfWeek='1'
                  height='550px'
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
                  actionBegin={onActionBegin.bind(this)} >
                  {console.log(lessons)}
                  {console.log(endTime)}
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
                  {console.log(subjectId)}

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
              </Grid.Column></Grid>
          </div>
          <Button onClick={() => createLessonOnSchedule()}>add</Button>
        </div>
      </div>
    </div>
  );
}