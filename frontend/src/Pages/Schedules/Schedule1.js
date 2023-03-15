import * as ReactDOM from 'react-dom';
import React, { useEffect, useState } from 'react';
import { ScheduleComponent, RecurrenceEditorComponent, ViewsDirective, ViewDirective, Day, Week, WorkWeek, Month, Inject, Schedule } from '@syncfusion/ej2-react-schedule';
import { DatePickerComponent } from '@syncfusion/ej2-react-calendars';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';
import { extend, L10n, loadCldr } from '@syncfusion/ej2-base';
import * as gregorian from 'cldr-data/main/lt/ca-gregorian.json';
import * as numbers from 'cldr-data/main/lt/numbers.json';
import * as timeZoneNames from 'cldr-data/main/lt/timeZoneNames.json';
import * as numberingSystems from 'cldr-data/supplemental/numberingSystems.json';
import * as weekData from 'cldr-data/supplemental/weekData.json';
loadCldr(numberingSystems, gregorian, numbers, timeZoneNames);

L10n.load({      
      "lt": {
        'datetimepicker': {
          placeholder: 'Pasirinkite datą',
          today: 'Šiandien'
      },
      "timeFormats": {        
        short: "HH"
      },
  }
});

export function ScheduleView1() {

  const [lessons, setLessons] = useState([]);
  const [schedule, setSchedule] = useState([]);


  useEffect(() => {
    fetch("/api/v1/schedule/1")
      .then((response) => response.json())
      .then((jsonRespones) => setSchedule(jsonRespones))
      .then(setLessons(schedule.lessons));
  }, []);


     let scheduleObj;
    let recurrObject;    
    const eventSettings = { dataSource: schedule };
    function onPopupOpen(args) {
        if (args.type === 'Editor') {
            scheduleObj.eventWindow.recurrenceEditor = recurrObject;
        }
    }
    function editorTemplate(props) {
        return (props !== undefined ? <table className="custom-event-editor" style={{ width: '100%' }}>
      <tbody>
        <tr><td className="e-textlabel">Pamoka</td><td colSpan={4}>
          <input id="Summary" className="e-field e-input" type="text" name="Subject" style={{ width: '100%' }}/>
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
    return (<ScheduleComponent timeFormat='HH' firstDayOfWeek='1' width='100%' height='550px' selectedDate={new Date(2018, 1, 15)} ref={schedule => scheduleObj = schedule} eventSettings={eventSettings} editorTemplate={editorTemplate} showQuickInfo={false} popupOpen={onPopupOpen}>
    <ViewsDirective>
      <ViewDirective option='Day' startHour='01:00' endHour='15:00' timeScale={{ slotCount: 1 }}/>
      <ViewDirective option='Week' startHour='01:00' endHour='15:00' timeScale={{ slotCount: 1 }}/>
      <ViewDirective option='WorkWeek'/>
      <ViewDirective option='Month'/>
    </ViewsDirective>
    <Inject services={[Day, Week, WorkWeek, Month]}/>
  </ScheduleComponent>);
}

// import * as React from 'react';
// import { HubConnectionBuilder } from '@microsoft/signalr';
// import { ScheduleComponent, Day, Week, WorkWeek, Month, Agenda, Inject, Resize, DragAndDrop } from '@syncfusion/ej2-react-schedule';
// import { extend } from '@syncfusion/ej2-base';
// import dataSource from './dataSource.json';
// /**
//  * Schedule realtime binding sample
//  */
// export function ScheduleView1() {
//     let connection;
//     const data = extend([], dataSource.scheduleData, null, true);
//     let isHubConnected = false;
//     let scheduleObj;
//     function onCreated() {
//         const url = 'https://ej2.syncfusion.com/aspnetcore/scheduleHub/';
//         connection = new HubConnectionBuilder().withUrl(url, { withCredentials: false }).withAutomaticReconnect().build();
//         connection.on('ReceiveData', (action, data) => {
//             if (action == 'view') {
//                 scheduleObj.currentView = data;
//             }
//             if (action === 'eventCreated' || action === 'eventChanged' || action === 'eventRemoved') {
//                 scheduleObj.eventSettings.dataSource = data;
//             }
//         });
//         connection.start().then(() => { isHubConnected = true; }).catch(() => { isHubConnected = false; });
//     }
//     function onNavigating(args) {
//         if (args.action == 'view' && isHubConnected) {
//             connection.invoke('SendData', args.action, args.currentView);
//         }
//     }
//     function onActionComplete(args) {
//         if (isHubConnected && (args.requestType === 'eventCreated' || args.requestType === 'eventChanged' || args.requestType === 'eventRemoved')) {
//             connection.invoke('SendData', args.requestType, scheduleObj.eventSettings.dataSource);
//         }
//     }
//     function componentWillUnmount() {
//         if (connection) {
//             connection.stop().then(() => { isHubConnected = false; }).catch((err) => { console.log(err); });
//         }
//     }
//     return (<div className='schedule-control-section'>
//             <div className='col-lg-12 control-section'>
//                 <div className='control-wrapper'>
//                     <ScheduleComponent ref={(schedule) => scheduleObj = schedule} height='550px' selectedDate={new Date(2021, 0, 10)} eventSettings={{ dataSource: data }} created={onCreated.bind(this)} actionComplete={onActionComplete.bind(this)} navigating={onNavigating.bind(this)}>
//                         <Inject services={[Day, Week, WorkWeek, Month, Agenda, Resize, DragAndDrop]}/>
//                     </ScheduleComponent>
//                 </div>
//             </div>
//         </div>);
// }
