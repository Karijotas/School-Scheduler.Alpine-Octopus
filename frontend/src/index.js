import React from 'react';
import ReactDOM from 'react-dom/client';
import { HashRouter, Route, Routes } from 'react-router-dom';
import 'semantic-ui-css/semantic.min.css';
import { ViewShifts } from './Pages/Edit/Shifts/ViewShifts';
import App from './App';
import './index.css';
import { CreateGroupPage } from './Pages/Edit/Groups/CreateGroupPage';
import { EditGroupObject } from './Pages/Edit/Groups/EditGroupObject';
import { ViewGroups } from './Pages/Edit/Groups/ViewGroups.js';
import { ViewModules } from './Pages/Edit/Module/ViewModules';
import { ViewPrograms } from './Pages/Edit/Programs/ViewPrograms';
import { ViewRooms } from './Pages/Edit/Rooms/ViewRooms';
import { CreateSubjecPage } from './Pages/Edit/Subjects/CreateSubjectPage';
import { EditSubjectObject } from './Pages/Edit/Subjects/EditSubjectObject';
import { ViewSubjects } from './Pages/Edit/Subjects/ViewSubjects';
import { ViewTeachers } from './Pages/Edit/Teachers/ViewTeachers';
import reportWebVitals from './reportWebVitals';





const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <HashRouter>
      <Routes>
        <Route path='/' element={<App />} />
        {/* EDIT MENU ROUTES */}

        {/* Subjects routes: */}
        <Route path='/view/subjects' element={<ViewSubjects />} />
        <Route path='/view/subjects/edit/:id' element={<EditSubjectObject />} />
        <Route path='/create/subjects' element={<CreateSubjecPage />} />

        {/* Teachers routes: */}
        <Route path='/view/teachers' element={<ViewTeachers />} />

        {/* Groups routes: */}
        <Route path='/view/groups' element={<ViewGroups />} />
        <Route path='/view/groups/edit/:id' element={<EditGroupObject />} />
        <Route path='/create/groups' element={<CreateGroupPage />} />

        {/* Shifts routes: */}
        <Route path='/view/shifts' element={<ViewShifts />} />

        {/* Rooms routes: */}
        <Route path='/view/rooms' element={<ViewRooms />} />

        {/* Programs routes: */}
        <Route path='/view/programs' element={<ViewPrograms />} />

        {/* Modules routes: */}
        <Route path='/view/modules' element={<ViewModules />} />



      </Routes>
    </HashRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
