import React, { useEffect, useState } from "react";
import { Button, Form, Icon, Input, Select, } from "semantic-ui-react";
import { ViewGroups } from "../Edit/EditPages/ViewGroups";


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



export function CreateGroupPage() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false)
  const [name, setName] = useState('');
  const [schoolYear, setSchoolYear] = useState('')
  const [studentAmount, setStudentAmount] = useState('')
  const [programs, setPrograms] = useState([])
  const [program, setProgram] = useState('')
  const [shift, setShift] = useState('')

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

  const createGroup = () => {
    fetch(
      '/api/v1/groups', {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        schoolYear,
        studentAmount,
        program,
        shift,
      })
    }).then(applyResult)
  };
  useEffect(() => {
    fetch('/api/v1/programs/')
      .then((response) => response.json())
      .then((data) =>
        setPrograms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);




  return (<div>
    {!hide && <div className="create-new-page">
      <Form >

        <Form.Field >
          <label>"Teams" grupės pavadinimas</label>
          <input placeholder='"Teams" grupės pavadinimas' value={name} onChange={(e) => setName(e.target.value)} />
        </Form.Field>
        <Form.Group widths='equal'>
          <Form.Field >
            <label>Mokslo metai</label>
            <Input options={yearOptions} placeholder='Mokslo metai' value={schoolYear} onChange={(e) => setSchoolYear(e.target.value)} />
          </Form.Field>
          <Form.Field >
            <label>Studentų skaičius</label>
            <Input placeholder='Studentų skaičius' value={studentAmount} onChange={(e) => setStudentAmount(e.target.value)} />
          </Form.Field> </Form.Group>
        <Form.Group widths='equal'>
          <Form.Field >
            <label>Programa</label>
            <Select options={programs} placeholder='Programa' value={program} onChange={(e) => setProgram(e.target.value)} />
          </Form.Field>
          <Form.Field >
            <label>Pamaina</label>
            <Input options={shiftOptions} placeholder='Pamaina' value={shift} onChange={(e) => setShift(e.target.value)} />
          </Form.Field>
        </Form.Group>
        <div><Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal</Button>
          <Button type='submit' className="controls" primary onClick={createGroup}>Sukurti</Button></div>
      </Form>
    </div>}
    {hide && (<div><ViewGroups /></div>)}
  </div>
  );
}

