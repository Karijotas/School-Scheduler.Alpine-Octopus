import './App.css';
import MenuExampleTabular from './Components/MainMenu';
import EditMenu from './Pages/Edit/EditMenu';
import * as React from "react";



function App() {
  return (
    <div className="App">

      <MenuExampleTabular />
      <EditMenu/>
    </div>
  );
}

export default App;
