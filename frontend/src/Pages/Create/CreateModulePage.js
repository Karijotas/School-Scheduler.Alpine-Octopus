
import React, { useEffect, useState } from "react";
import { Button, Form, Icon, Input, Select, } from "semantic-ui-react";
import { ViewModules } from "../Edit/EditPages/ViewModules";


const JSON_HEADERS = {
  'Content-Type': 'application/json'
};




const yearOptions = [
  { key: 23, value: 2023, text: '2023' },
  { key: 24, value: 2024, text: '2024' },
  { key: 25, value: 2025, text: '2025' },
  { key: 26, value: 2026, text: '2026' },
  { key: 27, value: 2027, text: '2027' },
  { key: 28, value: 2028, text: '2028' },
]


const shiftOptions = [
  { key: 'p', value: 'pirma', text: 'Pirma' },
  { key: 'a', value: 'antra', text: 'Antra' },
  { key: 't', value: 'trecia', text: 'Trečia' },
  { key: 'k', value: 'ketvirta', text: 'Ketvirta' },

]



export function CreateModulePage() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false)
  const [name, setName] = useState('');
  const [description, setDescription] = useState('')
  const [modulesSubjects, setModulesSubjects] = useState('')
  const [modules, setModules] = useState('')


  const applyResult = (result) => {
    const clear = () => {
      setHide(true)
    }

    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };

  const createModule = () => {
    fetch(
      '/api/v1/modules', {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
        modulesSubjects,
      })
    }).then(applyResult)
  };

  useEffect(() => {
    fetch('/api/v1/modules/')
      .then((response) => response.json())
      .then((data) =>
        setModules(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);


  const fetchModules = async () => {
    fetch(`/api/v1/modules/`)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
  };


  return (<div>
    {!hide && <div className="create-new-page">
      <Form >

        <Form.Field >
          <label>Modulio pavadinimas</label>
          <input placeholder='Modulio pavadinimas' value={name} onChange={(e) => setName(e.target.value)} />
        </Form.Field>
        <Form.Group widths='equal'>
          <Form.Field >
            <label>Aprašymas</label>
            <Input options={yearOptions} placeholder='Aprašymas' value={description} onChange={(e) => setDescription(e.target.value)} />
          </Form.Field>
           </Form.Group>
          <Form.Group grouped >
            <label>Pasirinktini dalykai:</label>
            {modules.map((module) => (
            <Form.Field label={module.name} control='input' type='checkbox' key={module.id}/>
            ))}
                </Form.Group>
        <div><Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal</Button>
          <Button type='submit' className="controls" primary onClick={createModule}>Sukurti</Button></div>
      </Form>
    </div>}
    {hide && (<div><ViewModules /></div>)}
  </div>
  );
}

