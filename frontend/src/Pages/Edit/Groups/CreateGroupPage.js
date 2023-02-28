import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment, Select } from "semantic-ui-react";
import MainMenu from '../../../Components/MainMenu';
import { EditMenu } from '../../../Components/EditMenu';
import { YEAR_OPTIONS } from '../../../Components/const';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};



const yearOptions = [
  { key: 23, value: 2023, text: "2023" },
  { key: 24, value: 2024, text: "2024" },
  { key: 25, value: 2025, text: "2025" },
  { key: 26, value: 2026, text: "2026" },
  { key: 27, value: 2027, text: "2027" },
  { key: 28, value: 2028, text: "2028" },
];

const shiftOptions = [
  { key: "p", value: "pirma", text: "Pirma" },
  { key: "a", value: "antra", text: "Antra" },
  { key: "t", value: "trecia", text: "Trečia" },
  { key: "k", value: "ketvirta", text: "Ketvirta" },
];

export function CreateGroupPage() {
  const listUrl = useHref('/view/groups');

  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false)
  const [name, setName] = useState('');
  const [schoolYear, setSchoolYear] = useState('')
  const [studentAmount, setStudentAmount] = useState('')
  const [programs, setPrograms] = useState([])
  const [programId, setProgramId] = useState()
  const [shift, setShift] = useState('')

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };

    if (result.ok) {
      clear()

    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };

  const createGroup = () => {
    fetch('/scheduler/api/v1/groups?programId=' + programId, {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        schoolYear,
        studentAmount,
        shift,
      }),
    }).then(applyResult).then(() => window.location = listUrl);

  };
  useEffect(() => {
    fetch("/scheduler/api/v1/programs/")
      .then((response) => response.json())
      .then((data) =>
        setPrograms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);


  return (<div className="create-new-page">
    <MainMenu />
    <Grid columns={2} >
      <Grid.Column width={2} id='main'>
        <EditMenu />
      </Grid.Column>
      <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
        <Segment id='segment' color='teal'>

          <Form >
            <Form.Field >
              <label>"Teams" grupės pavadinimas</label>
              <input placeholder='"Teams" grupės pavadinimas' value={name} onChange={(e) => setName(e.target.value)} />
            </Form.Field>
            <Form.Group widths='equal'>
              <Form.Field >
                <label>Mokslo metai</label>
                <select id='selectYear' value={schoolYear} onChange={(e) => setSchoolYear(e.target.value)} >
                  <option>-</option>
                  {Object.entries(YEAR_OPTIONS)
                    .map(([key, value]) => <option value={key}>{value}</option>)
                  }  </select>   </Form.Field>
              <Form.Field >
                <label>Studentų skaičius</label>
                <Input placeholder='Studentų skaičius' value={studentAmount} onChange={(e) => setStudentAmount(e.target.value)} />
              </Form.Field> </Form.Group>
            <Form.Group widths='equal'>
              <Form.Field>
                <label>Programa</label>
                <Select options={programs} placeholder='Programa' onClose={() => console.log(programId)} onChange={(e, data) => setProgramId(data.value)} />

              </Form.Field>
              <Form.Field >
                <label>Pamaina</label>
                <Input options={shiftOptions} placeholder='Pamaina' value={shift} onChange={(e) => setShift(e.target.value)} />
              </Form.Field>
            </Form.Group>
            <div ><Button icon labelPosition="left" className="" as={NavLink} exact to='/view/groups'><Icon name="arrow left" />Atgal</Button>
              <Button type='submit' className="controls" id='details' onClick={createGroup}>Sukurti</Button></div>
          </Form>

        </Segment>
      </Grid.Column>
    </Grid>
  </div>
  );
}
