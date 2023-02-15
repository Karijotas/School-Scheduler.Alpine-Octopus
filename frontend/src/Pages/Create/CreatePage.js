import { Dropdown, Form, Select } from "semantic-ui-react";
import MainMenu from "../../Components/MainMenu";
import * as React from "react";



const option = [
    { key: 'd', text: 'Dalykai', value: 'subjects' },
    { key: 'm', text: 'Mokytojai', value: 'teachers' },
    { key: 'g', text: 'Grupės', value: 'groups' },
    { key: 'p', text: 'Pamainos', value: 'shifts' },
    { key: 'k', text: 'Klasės', value: 'rooms' },
    { key: 'p', text: 'Programos', value: 'programs' },
    { key: 'md', text: 'Moduliai', value: 'modules' },

  ]
  
export function CreatePage(){
    return(
        <div className="create-new-page"> 
    <MainMenu />
    
    <Dropdown 
    placeholder="Pasirinkite:"
    search
    selection
    options={option}
  /> 
        </div>
      );

}