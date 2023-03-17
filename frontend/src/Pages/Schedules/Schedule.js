import { Agenda, Day, DragAndDrop, Inject, Month, Resize, ResourceDirective, ResourcesDirective, Schedule, ScheduleComponent, ViewDirective, ViewsDirective, Week, WorkWeek } from '@syncfusion/ej2-react-schedule';
import * as React from 'react';
import { Container } from "semantic-ui-react";
import './Schedule.css';





Schedule.Inject(Day, Week, WorkWeek, Month, Agenda, Resize, DragAndDrop);

export function ScheduleView() {

    const data = [{
            Id: 2,
            Subject: 'Matematika',
            StartTime: new Date(2021, 8, 15, 10, 0),
            EndTime: new Date(2021, 8, 15, 14, 0),
            CategoryColor: "#1aaa55",
            GroupId: 1
        },
        {
            Id: 1,
            Subject: "Velykų atostogos",
            StartTime: new Date(2021, 8, 16, 24, 0),
            EndTime: new Date(2021, 8, 24, 24, 0),
            IsAllDay: true,
            IsBlock: true,
            CategoryColor: "#357cd2",
            GroupId: 2
                   
            
        },
        {
            Id: 2,
            Subject: "Not Available",
            StartTime: new Date(2021, 8, 14, 10, 0),
            EndTime: new Date(2021, 8, 14, 12, 0),
            IsAllDay: false,
            IsBlock: true,
            
        }];

        const resourceData = [
            { GroupText: 'Group A', GroupId: 1, GroupColor: '#1aaa55' },
            { GroupText: 'Group B', GroupId: 2, GroupColor: '#357cd2' }
        ];
        
    return (
    <Container>
        <h1 className="title-text">Tvarkaraštis</h1>
    <ScheduleComponent timeFormat='HH' firstDayOfWeek='1' height='550px'  selectedDate={new Date(2021, 8, 15)} eventSettings={{ dataSource: data }} colorField='Color'>
    <ResourcesDirective>
              <ResourceDirective field='GroupId' title='Owner' name='Owners' dataSource={resourceData} textField='GroupText' idField='GroupId' colorField='GroupColor'>
              </ResourceDirective>
            </ResourcesDirective>
    <ViewsDirective>
      <ViewDirective option='Day' startHour='01:00' endHour='15:00' timeScale={{interval: 1, slotCount: 1 }}/>
      <ViewDirective option='Week' startHour='01:00' endHour='15:00'timeScale={{ slotCount: 1 }}/>
      <ViewDirective option='WorkWeek'/>
      <ViewDirective option='Month'/>
    </ViewsDirective>
    <Inject services={[Day, Week, WorkWeek, Month, Agenda]}/>
  </ScheduleComponent>
  

  </Container>);

  /**
   * schedule resources group-editing sample
   */
  


    //   let scheduleObj;
    //   let treeObj;
    //   let isTreeItemDropped = false;
    //   let draggedItemId = '';
    //   const allowDragAndDrops = true;
    //   const fields = { dataSource: dataSource.waitingList, id: 'Id', text: 'Name' };
    //   const data = extend([], dataSource.hospitalData, null, true);
    //   const departmentData = [
    //       { Text: 'GENERAL', Id: 1, Color: '#bbdc00' },
         
    //   ];
    //   const consultantData = [
    //       { Text: 'Alice', Id: 1, GroupId: 1, Color: '#bbdc00', Designation: 'Cardiologist' },

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
  