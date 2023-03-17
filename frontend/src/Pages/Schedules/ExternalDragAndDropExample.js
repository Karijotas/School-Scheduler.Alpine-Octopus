import { createRoot } from 'react-dom/client';
import * as React from 'react';
import { ScheduleComponent, ResourcesDirective, ResourceDirective, ViewsDirective, ViewDirective, Inject, TimelineViews, Resize, DragAndDrop, TimelineMonth } from '@syncfusion/ej2-react-schedule';

import { extend, closest, remove, addClass } from '@syncfusion/ej2-base';
import { updateSampleSection } from './sample-base';
import { TreeViewComponent } from '@syncfusion/ej2-react-navigations';
import dataSource from './dataSource.json';
import { useEffect, useState } from 'react';
/**
 * schedule resources group-editing sample
 */
function ExternalDragDrop() {
    React.useEffect(() => {
        updateSampleSection();
    }, []);
    let scheduleObj;
    let treeObj;
    let isTreeItemDropped = false;
    let draggedItemId = '';
    const allowDragAndDrops = true;   
     const [subjects, setSubjects] = useState([]);
useEffect(() => {
        fetch(`/api/v1/schedule/${1}/subjects`)
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

    const fields = { dataSource: subjectsOnSchedule, id: 'Id', text: 'Name' };
    const data = extend([], dataSource.hospitalData, null, true);
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

    
    function getConsultantName(value) {
        return value.resourceData[value.resource.textField];
    }
    function getConsultantImage(value) {
        return getConsultantName(value).toLowerCase();
    }
    function getConsultantDesignation(value) {
        return value.resourceData.Designation;
    }
    function resourceHeaderTemplate(props) {
        return (<div className="template-wrap"><div className="specialist-category"><div className={"specialist-image " + getConsultantImage(props)}></div><div className="specialist-name">
            {getConsultantName(props)}</div><div className="specialist-designation">{getConsultantDesignation(props)}</div></div></div>);
    }
    function treeTemplate(props) {
        return (<div id="waiting"><div id="waitdetails"><div id="waitlist">{props.Subject}</div>
            <div id="waitcategory">{props.LessonHours} - {props.Description}</div></div></div>);
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
            const filteredPeople = treeViewData.filter((item) => item.Id !== parseInt(draggedItemId, 1000));
            treeObj.fields.dataSource = filteredPeople;
            let elements = document.querySelectorAll('.e-drag-item.treeview-external-drag');
            for (let i = 0; i < elements.length; i++) {
                remove(elements[i]);
            }
        }
    }
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
    return (
    
    <div className='schedule-control-section'>
        <div className='col-lg-12 control-section'>
            <div className='control-wrapper drag-sample-wrapper'>
                <div className="schedule-container">
                    <div className="title-container">
                        <h1 className="title-text">Doctor's Appointments</h1>
                    </div>
                    <ScheduleComponent
                        ref={schedule => scheduleObj = schedule}
                        cssClass='schedule-drag-drop'
                        width='100%'
                        height='650px'
                        selectedDate={new Date(2021, 7, 2)}
                        currentView='TimelineDay'
                        resourceHeaderTemplate={resourceHeaderTemplate.bind(this)}
                        eventSettings={{
                            dataSource: data,
                            fields: {
                                subject: { title: 'Patient Name', name: 'Name' },
                                startTime: { title: "From", name: "StartTime" },
                                endTime: { title: "To", name: "EndTime" },
                                description: { title: 'Reason', name: 'Description' }
                            }

                        }}

                        group={{ enableCompactView: false, resources: ['Departments', 'Consultants'] }}
                        actionBegin={onActionBegin.bind(this)}>
                        <ResourcesDirective>
                            <ResourceDirective field='DepartmentID' title='Department' name='Departments' allowMultiple={false} dataSource={departmentData} textField='Text' idField='Id' colorField='Color'>
                            </ResourceDirective>
                            <ResourceDirective field='ConsultantID' title='Consultant' name='Consultants' allowMultiple={false} dataSource={consultantData} textField='Text' idField='Id' groupIDField="GroupId" colorField='Color'>
                            </ResourceDirective>
                        </ResourcesDirective>
                        <ViewsDirective>
                            <ViewDirective option='TimelineDay' />
                            <ViewDirective option='TimelineMonth' />
                        </ViewsDirective>
                        <Inject services={[TimelineViews, TimelineMonth, Resize, DragAndDrop]} />
                    </ScheduleComponent>
                </div>
                <div className="treeview-container">
                    <div className="title-container">
                        <h1 className="title-text">Waiting List</h1>
                    </div>

                    <TreeViewComponent ref={tree => treeObj = tree}
                        cssClass='treeview-external-drag'
                        dragArea=".drag-sample-wrapper"
                        nodeTemplate={treeTemplate.bind(this)}
                        fields={fields}
                        nodeDragStop={onTreeDragStop.bind(this)}
                        nodeSelecting={onItemSelecting.bind(this)}
                        nodeDragging={onTreeDrag.bind(this)}
                        nodeDragStart={onTreeDragStart.bind(this)}
                        allowDragAndDrop={allowDragAndDrops} />

                </div>
            </div>
        </div>
    </div>);
}
export default ExternalDragDrop;
