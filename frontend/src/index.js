import React from 'react';
import ReactDOM from 'react-dom/client';
import 'semantic-ui-css/semantic.min.css';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { HashRouter, Route, Routes } from 'react-router-dom';
import { EditMenu } from './Pages/Edit/EditMenu';
import { ViewGroups } from './Pages/Edit/Groups/ViewGroups.js';
import { ViewSubjects } from './Pages/Edit/Subjects/ViewSubjects';
import { ViewTeachers } from './Pages/Edit/EditPages/ViewTeachers'
import { ViewRooms } from './Pages/Edit/EditPages/ViewRooms';
import { ViewPrograms } from './Pages/Edit/EditPages/ViewPrograms';
import { ViewModules } from './Pages/Edit/EditPages/ViewModules';
import { EditGroupObject } from './Pages/Edit/Groups/EditGroupObject';
import { EditSubjectObject } from './Pages/Edit/Subjects/EditSubjectObject';
import { CreateGroupPage } from './Pages/Edit/Groups/CreateGroupPage';
import { CreateSubjecPage } from './Pages/Edit/Subjects/CreateSubjectPage';





const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <HashRouter>
      <Routes>
        <Route path='/' element={<App />} />
        {/* EDIT MENU ROUTES */}

        {/* Subjects routes: */}
        <Route path='/view/subjects' element={<ViewSubjects />} />
        <Route path='/view/subjects/edit/:id' element={<EditSubjectObject/>}/>
        <Route path='/create/subjects' element={<CreateSubjecPage/>} />

        {/* Teachers routes: */}
        <Route path='/view/teachers' element={<ViewTeachers />} />

        {/* Groups routes: */}
        <Route path='/view/groups' element={<ViewGroups />} />
        <Route path='/view/groups/edit/:id' element={<EditGroupObject />} />
        <Route path='/create/groups' element={<CreateGroupPage/>} />

        {/* Shifts routes: */}
        <Route path='/view/shifts' element={<EditMenu />} />

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
