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
