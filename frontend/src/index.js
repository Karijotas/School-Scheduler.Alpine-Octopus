import React from 'react';
import ReactDOM from 'react-dom/client';
import 'semantic-ui-css/semantic.min.css';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { HashRouter, Route, Routes } from 'react-router-dom';
import EditMenu from './Pages/Edit/EditMenu';
import { EditObject } from './Pages/Edit/EditPages/EditObject';
import { CreatePage } from './Pages/Create/CreatePage';
import { CreateModule } from './Pages/Create/CreateModule';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <HashRouter>
      <Routes>
        <Route path='/' element={<App />} />
        <Route path='/edit' element={<EditMenu />} />
        <Route path='/groups/edit/:id' element={<EditObject />} />
        <Route path='/create' element={<CreatePage />} />
        <Route path='/CreateNewModule' element={<CreateModule />} />
      
      </Routes>
    </HashRouter>



  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
