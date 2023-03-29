import React from 'react';
import ReactDOM from 'react-dom/client';
import { HashRouter, Route, Routes } from 'react-router-dom';
import 'semantic-ui-css/semantic.min.css';
import App from './App';
import './index.css';
import { CreateGroupPage } from './Pages/Edit/Groups/CreateGroupPage';
import { EditGroupObject } from './Pages/Edit/Groups/EditGroupObject';
import { ViewGroups } from './Pages/Edit/Groups/ViewGroups.js';
import { ViewModules } from './Pages/Edit/Module/ViewModules';
import { EditProgramObject } from './Pages/Edit/Programs/EditProgramObject';
import { ViewPrograms } from './Pages/Edit/Programs/ViewPrograms';
import { CreateRoom } from './Pages/Edit/Rooms/CreateRoom';
import { EditRoom } from './Pages/Edit/Rooms/EditRoom';
import { ViewRooms } from './Pages/Edit/Rooms/ViewRooms';
import { CreateShiftPage } from './Pages/Edit/Shifts/CreateShiftPage';
import { EditShiftObject } from './Pages/Edit/Shifts/EditShiftObject';
import { ViewShifts } from './Pages/Edit/Shifts/ViewShifts';
import { CreateSubjecPage } from './Pages/Edit/Subjects/CreateSubjectPage';
import { EditSubjectObject } from './Pages/Edit/Subjects/EditSubjectObject';
import { ViewSubjects } from './Pages/Edit/Subjects/ViewSubjects';
import { CreateTeacher } from './Pages/Edit/Teachers/CreateTeacher';
import { EditTeacherObject } from './Pages/Edit/Teachers/EditTeacherObject';
import { ViewTeachers } from './Pages/Edit/Teachers/ViewTeachers';
import { CreateProgramPage } from './Pages/Edit/Programs/CreateProgramPage';
import { EditModuleObject } from './Pages/Edit/Module/EditModuleObject';
import { CreateModulePage } from './Pages/Edit/Module/CreateModulePage';

import { CreateHoliday } from './Pages/Edit/Holidays/CreateHoliday';
import { EditHoliday } from './Pages/Edit/Holidays/EditHoliday';
import { ViewHolidays } from './Pages/Edit/Holidays/ViewHolidays';

import { ViewProgramsArchive } from './Pages/Edit/Programs/ViewProgramsArchive';
import { ViewSubjectsArchive } from './Pages/Edit/Subjects/ViewSubjectsArchive';
import { ViewTeachersArchive } from './Pages/Edit/Teachers/ViewTeachersArchive';
import { ViewGroupsArchive } from './Pages/Edit/Groups/ViewGroupsArchive';
import { ViewShiftsArchive } from './Pages/Edit/Shifts/ViewShiftsArchive';
import { ViewRoomsArchive } from './Pages/Edit/Rooms/ViewRoomsArchive';
import { ViewModulesArchive } from './Pages/Edit/Module/ViewModulesArchive';
import { ViewArchivedGroup } from './Pages/Edit/Groups/ViewArchivedGroup';
import { ViewArchivedModule } from './Pages/Edit/Module/ViewArchivedModule';
import { ViewArchivedRooms } from './Pages/Edit/Rooms/ViewArchivedRoom';
import { ViewArchivedProgram } from './Pages/Edit/Programs/ViewArchivedProgram';
import { ViewArchivedShift } from './Pages/Edit/Shifts/ViewArchivedShift';
import { ViewArchivedSubject } from './Pages/Edit/Subjects/ViewArchivedSubject';
import { ViewArchivedTeacher } from './Pages/Edit/Teachers/ViewArchivedTeachers';
import { ViewGroupsSchedules } from './Pages/Schedules/ViewGroupsSchedules';
import { ScheduleView } from './Pages/Schedules/Schedule';
import { Sched1 } from './Pages/Schedules/Sched1';

import { ScheduleView1 } from './Pages/Schedules/Schedule1';
import { registerLicense } from '@syncfusion/ej2-base';
import { EditGroupScheduleObject } from './Pages/Schedules/EditGroupScheduleObject';
import { CreateSchedule } from './Pages/Schedules/CreateSchedulePage';
import { CreateSchedulePageNext } from './Pages/Schedules/CreateSchedulePageNext';
import { Drag_Drop } from './Pages/Schedules/Drag&Drop';


// Registering Syncfusion license key
registerLicense('Mgo+DSMBaFt/QHRqVVhjVFpFdEBBXHxAd1p/VWJYdVt5flBPcDwsT3RfQF5jSn5adkFhXn9ccHFXTw==;Mgo+DSMBPh8sVXJ0S0J+XE9HflRDX3xKf0x/TGpQb19xflBPallYVBYiSV9jS31TdUVqWXtcd3RXRmZbWA==;ORg4AjUWIQA/Gnt2VVhkQlFadVdJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxQdkZjUH9acnNVRGBUVU0=;MTI4NzAxNUAzMjMwMmUzNDJlMzBQeVBLZVJjcHpDSjRFRXMxaWlWWUl1bnRtTzJOMFhqU3BsVno3ZWZlNDRNPQ==;MTI4NzAxNkAzMjMwMmUzNDJlMzBkbFJ3c0hROEg4V09pVHRBNEVVNUtKYTMzeWxzM0drL3MrU2xvSnBpdGVvPQ==;NRAiBiAaIQQuGjN/V0Z+WE9EaFxKVmJLYVB3WmpQdldgdVRMZVVbQX9PIiBoS35RdUVgWXZedHZSRmJdV0x/;MTI4NzAxOEAzMjMwMmUzNDJlMzBCRDNWaHVpbEV3ZUxvL2xZUWdNcTVNc0R1dTRBRDlvZmdFRUV3bG5JUnYwPQ==;MTI4NzAxOUAzMjMwMmUzNDJlMzBLbUZsVkZCdHZwSUgvNVVyd3lLbUZYR0tua3pPTDN0Mml4ak5PdXBROEpBPQ==;Mgo+DSMBMAY9C3t2VVhkQlFadVdJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxQdkZjUH9acnNVRGJYUk0=;MTI4NzAyMUAzMjMwMmUzNDJlMzBXUDhkYm5QM3RTMXhTSHg0R3pRNHM5bXNjbXN4WjJxa3JGQ2xjRlJHVnRvPQ==;MTI4NzAyMkAzMjMwMmUzNDJlMzBhUW9IZGZSdEFLL0VGd1gwY3A2TnY4dEVqUVNGc1Y1dTM2WWNESUJSdUtBPQ==;MTI4NzAyM0AzMjMwMmUzNDJlMzBCRDNWaHVpbEV3ZUxvL2xZUWdNcTVNc0R1dTRBRDlvZmdFRUV3bG5JUnYwPQ==');



// basename={process.env.REACT_APP_PUBLIC_URL}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <HashRouter basename={process.env.REACT_APP_PUBLIC_URL}>
      <Routes>
        <Route path='/' element={<App />} />
        {/* EDIT MENU ROUTES */}

        {/* Subjects routes: */}
        <Route path='/view/subjects' element={<ViewSubjects />} />
        <Route path='/view/subjects/edit/:id' element={<EditSubjectObject />} />
        <Route path='/create/subjects' element={<CreateSubjecPage />} />

        {/* Teachers routes: */}
        <Route path='/view/teachers' element={<ViewTeachers />} />
        <Route path='/view/teachers/edit/:id' element={<EditTeacherObject />} />
        <Route path='/create/teachers/' element={<CreateTeacher />} />


        {/* Groups routes: */}
        < Route path='/view/groups' element={<ViewGroups />} />
        <Route path='/view/groups/edit/:id' element={<EditGroupObject />} />
        <Route path='/create/groups' element={<CreateGroupPage />} />

        {/* Shifts routes: */}
        <Route path='/view/shifts' element={<ViewShifts />} />
        <Route path='/view/shifts/edit/:id' element={<EditShiftObject />} />
        <Route path='/create/shifts' element={<CreateShiftPage />} />

        {/* Rooms routes: */}
        <Route path='/view/rooms' element={<ViewRooms />} />
        <Route path='/view/rooms/edit/:id' element={<EditRoom />} />
        <Route path='/create/rooms' element={<CreateRoom />} />

        {/* Programs routes: */}
        <Route path='/view/programs' element={<ViewPrograms />} />
        <Route path='/view/programs/edit/:id' element={<EditProgramObject />} />
        <Route path='/create/programs' element={<CreateProgramPage />} />

        {/* Modules routes: */}
        <Route path='/view/modules' element={<ViewModules />} />
        <Route path='/view/modules/edit/:id' element={<EditModuleObject />} />
        <Route path='/create/modules' element={<CreateModulePage />} />

        {/* Holidays routes: */}
        <Route path='/view/holidays' element={<ViewHolidays />} />
        <Route path='/view/holidays/edit/:id' element={<EditHoliday />} />
        <Route path='/create/holidays' element={<CreateHoliday />} />

        {/* Archive routes: */}
        <Route path='/view/archives/subjects' element={<ViewSubjectsArchive />} />
        <Route path='/view/archives/subjects/:id' element={<ViewArchivedSubject />} />
        <Route path='/view/archives/teachers' element={<ViewTeachersArchive />} />
        <Route path='/view/archives/teachers/:id' element={<ViewArchivedTeacher />} />
        <Route path='/view/archives/groups' element={<ViewGroupsArchive />} />
        <Route path='/view/archives/groups/:id' element={<ViewArchivedGroup />} />
        <Route path='/view/archives/shifts' element={<ViewShiftsArchive />} />
        <Route path='/view/archives/shifts/:id' element={<ViewArchivedShift />} />
        <Route path='/view/archives/rooms' element={<ViewRoomsArchive />} />
        <Route path='/view/archives/rooms/:id' element={<ViewArchivedRooms />} />
        <Route path='/view/archives/programs' element={<ViewProgramsArchive />} />
        <Route path='/view/archives/programs/:id' element={<ViewArchivedProgram />} />
        <Route path='/view/archives/modules' element={<ViewModulesArchive />} />
        <Route path='/view/archives/modules/:id' element={<ViewArchivedModule />} />

        {/* Group schedules routes: */}
        <Route path='/view/groupsSchedules' element={<ViewGroupsSchedules />} />
        <Route path='/view/groupsSchedules/edit/:id' element={<EditGroupScheduleObject />} />


        <Route path='/create/groupsSchedules' element={<CreateSchedule />} />
        <Route path='/create/groupsSchedules/modify/:id' element={<CreateSchedulePageNext />} />

      </Routes>
    </HashRouter>
  </React.StrictMode>
);
