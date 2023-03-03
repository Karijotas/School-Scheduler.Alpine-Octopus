import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment, Select } from "semantic-ui-react";
import { YEAR_OPTIONS } from '../../../Components/const';
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

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
  const [schoolYear, setSchoolYear] = useState(2023)
  const [studentAmount, setStudentAmount] = useState('')
  const [programs, setPrograms] = useState([])
  const [programId, setProgramId] = useState()
  const [shifts, setShifts] = useState([]);
  const [shiftId, setShiftId] = useState();

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [studentDirty, setStudentDirty] = useState(false);

  const [nameError, setNameError] = useState("Negali būti tuščias!")
  const [studentError, setStudentError] = useState("")
  // const [yearError, setYearError] = useState("*Privaloma")
  // const [programError, setProgramError] = useState("*Privaloma")
  // const [shiftError, setShiftError] = useState("*Privaloma")

  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if (studentError || nameError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [studentError, nameError,])

  const blurHandler = (e) => {
    switch (e.target.name) {
      case 'name':
        setNameDirty(true);
        break
      case 'student':
        setStudentDirty(true);
        break
    }
  }

  const nameHandler = (e) => {
    setName(e.target.value)
    if (e.target.value.length < 2 || e.target.value.length > 100) {
      setNameError("Įveskite nuo 2 iki 100 simbolių!")
      if (!e.target.value) {
        setNameError("Negali būti tuščias!")
      }
    } else {
      setNameError("")
    }
  }
  const studentHandler = (e) => {
    setStudentAmount(e.target.value)
    if (!e.target.value) {
      setStudentError("Negali būti tuščias!")
      if (!/^\d+$/.test(e.target.value)) {
        setStudentError("Įveskite tik skaičius")
      }
    }

  }


  // const selectShiftHandler = () => {
  //   setShiftError("")
  // }

  // const selectProgramHandler = () => {
  //   setProgramError("")
  // }

  // const selectYearHandler = () => {
  //   setYearError("")
  //     }



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
    fetch(
      '/api/v1/groups?programId=' + programId + '&shiftId=' + shiftId, {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        schoolYear,
        studentAmount,
      }),
    }).then(applyResult).then(() => window.location = listUrl);

  };
  useEffect(() => {
    fetch("/api/v1/programs/")
      .then((response) => response.json())
      .then((data) =>
        setPrograms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [shifts]);

  useEffect(() => {
    fetch('/api/v1/shifts')
      .then((response) => response.json())
      .then((data) => setShifts(
        data.map((x) => {
          return { key: x.id, text: x.name, value: x.id };
        })
      )
      )
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
              {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
              <input name="name" onBlur={blurHandler} placeholder='"Teams" grupės pavadinimas' value={name} onChange={(e) => nameHandler(e)} />
            </Form.Field>
            <Form.Group widths='equal'>
              <Form.Field >
                <label>Mokslo metai</label>
                {/* {(yearError) && <div style={{color: "red"}}>{yearError}</div>} */}
                <select id='selectYear' value={schoolYear} onChange={((e) => setSchoolYear(e.target.value))} >
                  {Object.entries(YEAR_OPTIONS)
                    .map(([key, value]) => <option value={key}>{value}</option>)
                  }  </select>   </Form.Field>
              <Form.Field >
                {(studentError) && <div style={{ color: "red" }}>{studentError}</div>}
                <label>Studentų skaičius</label>
                <Input name="student" placeholder='Studentų skaičius' value={studentAmount} onChange={(e) => studentHandler(e)} />
              </Form.Field> </Form.Group>
            <Form.Group widths='equal'>
              <Form.Field>
                <label>Programa</label>
                {/* {(programError) && <div style={{ color: "red" }}>{programError}</div>} */}
                <Select options={programs} placeholder='Programa' onClose={() => console.log(programId)} onChange={(e, data) => setProgramId(data.value)} />

              </Form.Field>
              <Form.Field >
                <label>Pamaina</label>
                {/* {(shiftError) && <div style={{ color: "red" }}>{shiftError}</div>} */}
                <Select options={shifts} placeholder='Pamaina' onClose={() => console.log(shiftId)} onChange={(e, data) => setShiftId(data.value)} />
              </Form.Field>
            </Form.Group>
            <div ><Button icon labelPosition="left" className="" as={NavLink} exact to='/view/groups'><Icon name="arrow left" />Atgal</Button>
              <Button type='submit' disabled={!formValid} className="controls" id='details' onClick={createGroup}>Sukurti</Button></div>
          </Form>

        </Segment>
      </Grid.Column>
    </Grid>
  </div>
  );
}
