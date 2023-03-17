import { TreeViewComponent } from '@syncfusion/ej2-react-navigations';
import { Agenda, Day, DragAndDrop, ExcelExport, Inject, Month, Print, Resize, ResourceDirective, ResourcesDirective, Schedule, ScheduleComponent, ViewDirective, ViewsDirective, Week, WorkWeek, TimelineMonth, TimelineViews } from '@syncfusion/ej2-react-schedule';
import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { Container, Grid } from "semantic-ui-react";
import "../../../node_modules/@syncfusion/ej2-icons/styles/bootstrap5.css";
import './Schedule.css';
import dataSource from './dataSource.json';
import { Tree } from "antd";
import { updateSampleSection } from "./sample-base";
import { extend, addClass, remove, closest } from '@syncfusion/ej2-base';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';
import { DatePickerComponent } from '@syncfusion/ej2-react-calendars';



Schedule.Inject(Day, Week, WorkWeek, Month, Agenda, Resize, DragAndDrop, Print, ExcelExport);

export function ScheduleView() {
  let scheduleObj;
  const params = useParams();
  const [lesson, setLesson] = useState({});
  const [oneLesson, setOneLesson] = useState({
    Id: "",
    Subject: "",
    StartTime: "",
    EndTime: "",
    ColorId: 1


  });
  let treeObj;
  let isTreeItemDropped = false;
  let draggedItemId = '';
  const allowDragAndDrops = true;
  useEffect(() => {
    updateSampleSection();
  }, []);

  function treeTemplate(props) {
    return (<div id="waiting"><div id="waitdetails"><div id="waitlist">{props.Subject}</div>
      <div id="waitcategory">{props.LessonHours} val {props.id}</div></div></div>);
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
  function editorTemplate(props) {
    return (props !== undefined ? <table className="custom-event-editor" style={{ width: '100%' }}>
      <tbody>
        <tr><td className="e-textlabel">Pamoka</td><td colSpan={4}>
          <input id="Summary" className="e-field e-input" type="text" name="Subject" style={{ width: '100%' }} />
        </td></tr>
        <tr><td className="e-textlabel">Nuotolinė pamoka</td><td colSpan={4}>
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
        </td></tr>
        <tr><td className="e-textlabel">Nuo</td><td colSpan={4}>
          <DatePickerComponent format='dd/MM/yy' timeFormat={"HH"} step={60} locale='lt' id="StartTime" data-name="StartTime" value={new Date(props.startTime || props.StartTime)} className="e-field"></DatePickerComponent>
        </td></tr>
        <tr><td className="e-textlabel">Iki</td><td colSpan={4}>
          <DatePickerComponent locale='lt' format='dd/MM/yy' timeFormat={"HH"} step={60} id="EndTime" data-name="EndTime" value={new Date(props.endTime || props.EndTime)} className="e-field"></DatePickerComponent>
        </td></tr>
        <tr><td className="e-textlabel">Pamoka nuo</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']}>
          </DropDownListComponent>
        </td></tr>
        <tr><td className="e-textlabel">Pamoka iki</td><td colSpan={4}>
          <DropDownListComponent id="EventType" placeholder='Pasirinkti' data-name="Status" className="e-field" style={{ width: '100%' }} dataSource={['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']}>
          </DropDownListComponent>
        </td></tr>

        <tr><td className="e-textlabel">Pastabos</td><td colSpan={4}>
          <textarea id="Description" className="e-field e-input" name="Description" rows={3} cols={50} style={{ width: '100%', height: '60px !important', resize: 'vertical' }}></textarea>
        </td></tr></tbody>
    </table> : '');
  }
  const [subjects, setSubjects] = useState([]);

  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/subjects`)
      .then((response) => response.json())
      // .then((jsonRespones) => setLessons(jsonRespones))
      .then(setSubjects)
  }, []);


  const subjectsOnSchedule = subjects.map(s => {
    return {
      Id: s.id,
      Subject: s.subject.name,
      LessonHours: s.lessonHours
    }
  })
  function onTreeDragStop(event) {
    let treeElement = closest(event.target, '.e-treeview');
    let classElement = scheduleObj.element.querySelector('.e-device-hover');
    if (classElement) {
      classElement.classList.remove('e-device-hover');
    }
    if (!treeElement) {
      event.cancel = true;
      let scheduleElement = closest(event.target, '.e-content-wrap');
      if (scheduleElement) {
        let treeviewData = treeObj.fields.dataSource;
        if (event.target.classList.contains('e-work-cells')) {
          const filteredData = treeviewData.filter((item) => item.Id === parseInt(event.draggedNodeData.id, 10));
          let cellData = scheduleObj.getCellDetails(event.target);
          let eventData = {
            Name: filteredData[0].Subject,
            StartTime: cellData.startTime,
            EndTime: cellData.endTime,

          };
          scheduleObj.openEditor(eventData, 'Add', true);
          isTreeItemDropped = true;
          draggedItemId = event.draggedNodeData.id;
        }
      }
    }
    document.body.classList.remove('e-disble-not-allowed');
  }
  function onTreeDragStart() {
    document.body.classList.add('e-disble-not-allowed');
  }

  const [lessons, setLessons] = useState([]);

  // function onActionBegin(args) {
  //   if (args.requestType === 'toolbarItemRendering') {
  //     let exportItem = {
  //       align: 'Right', showTextOn: 'Both', prefixIcon: "e-icons e-print",
  //       text: 'Spausdinti', cssClass: 'e-schedule-print', click: onPrintIconClick,
  //       // align: 'Center', showTextOn: 'Both', prefixIcon: 'e-icon-schedule-excel-export',
  //       // text: 'Excel Export', cssClass: 'e-excel-export', click: onExportClick
  //     };
  //     args.items.push(exportItem);
  //   }
  // }
  // function onPrintIconClick() {
  //   scheduleObj.print();
  // }

  // function onExportClick() {
  //   scheduleObj.exportToExcel();
  // }

  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/lessons`)
      .then((response) => response.json())
      // .then((jsonRespones) => setLessons(jsonRespones))
      .then(setLessons)
  }, []);


  const lessonsOnSchedule = lessons.map(l => {
    return {
      Id: l.id,
      Subject: l.subject.name,
      StartTime: l.startTime,
      EndTime: l.endTime,
      ColorId: 1
    }
  })


  const [updated, setUpdated] = useState();
  const fields = { dataSource: subjectsOnSchedule, id: 'Id', text: 'Name' };
  const data = extend([], { dataSource: lessonsOnSchedule }, null, true);

  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);



  // function change(args) {
  //     scheduleObj.selectedDate = args.value;
  //     scheduleObj.dataBind();
  // }
  // function onDragStart(args) {
  //     args.navigation.enable = true;
  // }

  // const [lessons, setLessons] = useState([]);
  // const [schedule, setSchedule] = useState([]);

  // useEffect(() => {
  //     fetch("/api/v1/schedule/1")
  //       .then((response) => response.json())
  //       .then((jsonRespones) => setSchedule(jsonRespones))
  //       .then(setLessons(schedule.lessons));
  //   }, []);

  //   const dataManager = new DataManager({
  //     url: "https://ej2services.syncfusion.com/production/web-services/api/Schedule",
  //     adaptor: new WebApiAdaptor,
  //     crossDomain: true
  // });

  // const data = [{
  //   oneLesson
  //   // Id: lesson.id,
  //   // Subject: lesson.subject.name,
  //   // StartTime: lesson.startTime,
  //   // EndTime: lesson.endTime,            
  //   // ColorId: 1
  // },

  // {
  //   Id: 1,
  //   Subject: "Velykų atostogos",
  //   StartTime: new Date(2023, 1, 10, 24, 0),
  //   EndTime: new Date(2023, 1, 24, 24, 0),
  //   IsAllDay: true,
  //   IsBlock: true,
  //   CategoryColor: "#357cd2",
  //   ColorId: 2


  // },
  // {
  //   Id: 2,
  //   Subject: "Not Available",
  //   StartTime: new Date(2023, 1, 6, 10, 0),
  //   EndTime: new Date(2023, 1, 6, 12, 0),
  //   IsAllDay: false,
  //   IsBlock: true,

  // }];
  
  function resourceHeaderTemplate(props) {
    return (<div className="template-wrap"><div className="specialist-category"><div className={"specialist-image " + props}></div><div className="specialist-name">
      {props}</div><div className="specialist-designation">{props}</div></div></div>);
  }

  const departmentData = [
    { Text: 'GENERAL', Id: 1, Color: '#bbdc00' },
    { Text: 'DENTAL', Id: 2, Color: '#9e5fff' }
  ];
  const consultantData = [
    { Text: 'Alice', Id: 1, GroupId: 1, Color: '#bbdc00', Designation: 'Cardiologist' },
    { Text: 'Nancy', Id: 2, GroupId: 2, Color: '#9e5fff', Designation: 'Orthodontist' },
    { Text: 'Robert', Id: 3, GroupId: 1, Color: '#bbdc00', Designation: 'Optometrist' },
    { Text: 'Robson', Id: 4, GroupId: 2, Color: '#9e5fff', Designation: 'Periodontist' },
    { Text: 'Laura', Id: 5, GroupId: 1, Color: '#bbdc00', Designation: 'Orthopedic' },
    { Text: 'Margaret', Id: 6, GroupId: 2, Color: '#9e5fff', Designation: 'Endodontist' }
  ];
  const resourceData = [
    { GroupText: 'Group A', ColorId: 1, GroupColor: '#1aaa55' },
    { GroupText: 'Group B', ColorId: 2, GroupColor: '#357cd2' }
  ];

  return (
    // <Grid columns={2} stretched>
    <div className='schedule-control-section'>
      <div className='col-lg-12 control-section'>
        <div className='control-wrapper drag-sample-wrapper'>
          {/* <Grid.Column stretched width={13}> */}
          <div className="schedule-container">

            <h1 className="title-text">Tvarkaraštis</h1>
            <ScheduleComponent
              ref={schedule => scheduleObj = schedule}
              cssClass='schedule-drag-drop'
              width='100%'
              height='650px'
              selectedDate={new Date(2021, 7, 2)}
              currentView='Month'
              resourceHeaderTemplate={resourceHeaderTemplate.bind(this)}
              editorTemplate={editorTemplate}
              eventSettings={{
                dataSource: data,
                fields: {
                  editorTemplate
                }

              }}

              actionBegin={onActionBegin.bind(this)} >
              {/* {console.log(lessonsOnSchedule)} */}
              <ResourcesDirective>

                /
              </ResourcesDirective>
              <ViewsDirective>
                <ViewDirective option='Day' />
                <ViewDirective option='Week' />
                <ViewDirective option='Month' />
              </ViewsDirective>
              <Inject services={[TimelineViews, TimelineMonth, Day, Week, WorkWeek, Month, Agenda, DragAndDrop, ExcelExport, Print, DragAndDrop]} />



            </ScheduleComponent>
          </div>


          {/* </Grid.Column> */}

          {/* <Grid.Column width={3}> */}
          <div className="treeview-container">
            <TreeViewComponent ref={tree => treeObj = tree}
              cssClass='treeview-external-drag'
              dragArea=".drag-sample-wrapper"
              nodeTemplate={treeTemplate.bind(this)}
              fields={{ dataSource: subjectsOnSchedule, id: 'Id', text: 'Name' }}
              nodeDragStop={onTreeDragStop.bind(this)}
              nodeSelecting={onItemSelecting.bind(this)}
              nodeDragging={onTreeDrag.bind(this)}
              nodeDragStart={onTreeDragStart.bind(this)}
              allowDragAndDrop={allowDragAndDrops} />
          </div>
          {/* </Grid.Column> */}
        </div>
      </div>
    </div>
    // </Grid>

  );

  /**
   * schedule resources group-editing sample
   */



  //   let scheduleObj;
  //   let treeObj;
  //   let isTreeItemDropped = false;
  //   let draggedItemId = '';
  //   const allowDragAndDrops = true;
  //   const fields = {dataSource: dataSource.waitingList, id: 'Id', text: 'Name' };
  //   const data = extend([], dataSource.hospitalData, null, true);
  //   const departmentData = [
  //       {Text: 'GENERAL', Id: 1, Color: '#bbdc00' },

  //   ];
  //   const consultantData = [
  //       {Text: 'Alice', Id: 1, GroupId: 1, Color: '#bbdc00', Designation: 'Cardiologist' },

  //   ];
  //   function getConsultantName(value) {
  //       return value.resourceData[value.resource.textField];
  //   }
  //   function getConsultantImage(value) {
  //       return getConsultantName(value).toLowerCase();
  //   }
  //   function getConsultantDesignation(value) {
  //       return value.resourceData.Designation;
  //   }
  //   function resourceHeaderTemplate(props) {
  //       return (<div className="template-wrap"><div className="specialist-category"><div className={"specialist-image " + getConsultantImage(props)}></div><div className="specialist-name">
  //     {getConsultantName(props)}</div><div className="specialist-designation">{getConsultantDesignation(props)}</div></div></div>);
  //   }
  //   function treeTemplate(props) {
  //       return (<div id="waiting"><div id="waitdetails"><div id="waitlist">{props.Name}</div>
  //     <div id="waitcategory">{props.DepartmentName} - {props.Description}</div></div></div>);
  //   }
  //   function onItemSelecting(args) {
  //       args.cancel = true;
  //   }
  //   function onTreeDrag(event) {
  //       if (scheduleObj.isAdaptive) {
  //           let classElement = scheduleObj.element.querySelector('.e-device-hover');
  //           if (classElement) {
  //               classElement.classList.remove('e-device-hover');
  //           }
  //           if (event.target.classList.contains('e-work-cells')) {
  //               addClass([event.target], 'e-device-hover');
  //           }
  //       }
  //   }
  //   function onActionBegin(event) {
  //       if (event.requestType === 'eventCreate' && isTreeItemDropped) {
  //           let treeViewData = treeObj.fields.dataSource;
  //           const filteredPeople = treeViewData.filter((item) => item.Id !== parseInt(draggedItemId, 10));
  //           treeObj.fields.dataSource = filteredPeople;
  //           let elements = document.querySelectorAll('.e-drag-item.treeview-external-drag');
  //           for (let i = 0; i < elements.length; i++) {
  //               remove(elements[i]);
  //           }
  //       }
  //   }
  //   function onTreeDragStop(event) {
  //       let treeElement = closest(event.target, '.e-treeview');
  //       let classElement = scheduleObj.element.querySelector('.e-device-hover');
  //       if (classElement) {
  //           classElement.classList.remove('e-device-hover');
  //       }
  //       if (!treeElement) {
  //           event.cancel = true;
  //           let scheduleElement = closest(event.target, '.e-content-wrap');
  //           if (scheduleElement) {
  //               let treeviewData = treeObj.fields.dataSource;
  //               if (event.target.classList.contains('e-work-cells')) {
  //                   const filteredData = treeviewData.filter((item) => item.Id === parseInt(event.draggedNodeData.id, 10));
  //                   let cellData = scheduleObj.getCellDetails(event.target);
  //                   let resourceDetails = scheduleObj.getResourcesByIndex(cellData.groupIndex);
  //                   let eventData = {
  //                       Name: filteredData[0].Name,
  //                       StartTime: cellData.startTime,
  //                       EndTime: cellData.endTime,
  //                       IsAllDay: cellData.isAllDay,
  //                       Description: filteredData[0].Description,
  //                       DepartmentID: resourceDetails.resourceData.GroupId,
  //                       ConsultantID: resourceDetails.resourceData.Id
  //                   };
  //                   scheduleObj.openEditor(eventData, 'Add', true);
  //                   isTreeItemDropped = true;
  //                   draggedItemId = event.draggedNodeData.id;
  //               }
  //           }
  //       }
  //       document.body.classList.remove('e-disble-not-allowed');
  //   }
  //   function onTreeDragStart() {
  //       document.body.classList.add('e-disble-not-allowed');
  //   }
  //   return (
  //   <Container>
  //   <div className='schedule-control-section'>
  //     <div className='col-lg-12 control-section'>
  //       <div className='control-wrapper drag-sample-wrapper'>
  //         <div className="schedule-container">
  //           <div className="title-container">
  //             <h1 className="title-text">Tvarkaraštis</h1>
  //           </div>
  //           <ScheduleComponent ref={schedule => scheduleObj = schedule} timeFormat='HH' firstDayOfWeek='1' cssClass='schedule-drag-drop' width='100%' height='650px' selectedDate={new Date(2021, 7, 2)} currentView='TimelineDay' resourceHeaderTemplate={resourceHeaderTemplate.bind(this)} eventSettings={{
  //           dataSource: data,
  //           fields: {
  //               subject: { title: 'Patient Name', name: 'Name' },
  //               startTime: { title: "From", name: "StartTime" },
  //               endTime: { title: "To", name: "EndTime" },
  //               description: { title: 'Reason', name: 'Description' }
  //           }
  //       }} group={{ enableCompactView: false, resources: ['Departments', 'Consultants'] }} actionBegin={onActionBegin.bind(this)}>
  //             <ResourcesDirective>                 
  //               <ResourceDirective field='ConsultantID' title='Consultant' name='Consultants' allowMultiple={false} dataSource={consultantData} textField='Text' idField='Id' groupIDField="GroupId" colorField='Color'>
  //               </ResourceDirective>
  //             </ResourcesDirective>
  //             <ViewsDirective>
  //               <ViewDirective option='Day' startHour='01:00' endHour='15:00' timeScale={{interval: 1, slotCount: 1 }}/>
  //               <ViewDirective option='TimelineDay'/>
  //               <ViewDirective option='Week' startHour='01:00' endHour='15:00' timeScale={{interval: 1, slotCount: 1 }}/>
  //               <ViewDirective option='TimelineMonth'/>
  //             </ViewsDirective>
  //             <Inject services={[Day, Week, TimelineViews, TimelineMonth, Resize, DragAndDrop]}/>
  //           </ScheduleComponent>
  //         </div>

  //       </div>
  //     </div>
  //   </div>
  //   </Container>);
}
