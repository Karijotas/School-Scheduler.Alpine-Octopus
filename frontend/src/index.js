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
import { ViewProgramsArchive } from './Pages/Edit/Programs/ViewProgramsArchive';
import { ViewSubjectsArchive } from './Pages/Edit/Subjects/ViewSubjectsArchive';
import { ViewTeachersArchive } from './Pages/Edit/Teachers/ViewTeachersArchive';
import { ViewGroupsArchive } from './Pages/Edit/Groups/ViewGroupsArchive';
import { ViewShiftsArchive } from './Pages/Edit/Shifts/ViewShiftsArchive';
import { ViewRoomsArchive } from './Pages/Edit/Rooms/ViewRoomsArchive';
import { ViewModulesArchive } from './Pages/Edit/Module/ViewModulesArchive';







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

        {/* Archive routes: */}
        <Route path='/view/archives/subjects' element={<ViewSubjectsArchive />} />
        <Route path='/view/archives/teachers' element={<ViewTeachersArchive />} />
        <Route path='/view/archives/groups' element={<ViewGroupsArchive />} />
        <Route path='/view/archives/shifts' element={<ViewShiftsArchive />} />
        <Route path='/view/archives/rooms' element={<ViewRoomsArchive />} />
        <Route path='/view/archives/programs' element={<ViewProgramsArchive />} />
        <Route path='/view/archives/modules' element={<ViewModulesArchive />} />

      </Routes>
    </HashRouter>
  </React.StrictMode>
);
