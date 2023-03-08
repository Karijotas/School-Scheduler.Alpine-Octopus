import React, { useState, useEffect } from "react";
import {
  Button,
  Form, Grid, Icon, Segment, TextArea
} from "semantic-ui-react";

import { NavLink, useHref } from 'react-router-dom';
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

export function CreateProgramPage() {
  // const [create, setCreate] = useState()

  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [programs, setPrograms] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectHours, setSubjectHours] = [];
  const [error, setError] = useState();
  const [hours, setHours] = useState("");
  const [createdProgram, setCreatedProgram] = useState();
  const listUrl = useHref('/view/programs/edit/');

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [nameError, setNameError] = useState("Negali būti tuščias!")
  const [descriptionError, setDescriptionError] = useState("")
  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if (nameError || descriptionError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, descriptionError])


  const blurHandler = (e) => {
    switch (e.target.name) {
      case 'name':
        setNameDirty(true);
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

  const descriptionHandler = (e) => {
    setDescription(e.target.value)
    if (e.target.value.length > 500) {
      setDescriptionError("Aprašymas negali viršyti 500 simbolių!")
    } else {
      setDescriptionError("")
    }
  }

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };
    if (result.ok) {
     let info = result.json() 
      .then((jsonResponse) => window.location = listUrl + jsonResponse.id);
    } else {
      window.alert("Nepavyko sukurti: pavadinimas turi būti unikalus!");
    }
  };

  // const fetchPrograms = () => {
  //   fetch("/api/v1/programs")
  //     .then((response) => response.json())
  //     .then((jsonResponse) => setPrograms(jsonResponse))
  // };

  const createProgram = () => {
    fetch("/api/v1/programs", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(applyResult)
  };


  return (
    <div>
      <MainMenu />

      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>

        <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' color='teal'>

            <div className="create-new-page">
              <Form>
                <Form.Field>
                  <label>Programos pavadinimas</label>
                  {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
                  <input
                    name="name"
                    placeholder="Programos pavadinimas"
                    onBlur={blurHandler}
                    value={name}
                    onChange={(e) => nameHandler(e)}
                  />
                </Form.Field>
                <Form.Group widths="equal">
                  <Form.Field>
                    <label>Aprašymas</label>
                    {(descriptionError) && <div style={{ color: "red" }}>{descriptionError}</div>}
                    <TextArea
                      placeholder="Aprašymas"
                      style={{ minHeight: 100 }}
                      value={description}
                      onChange={(e) => descriptionHandler(e)}
                    />
                  </Form.Field>
                </Form.Group>

                <div>
                  <Button
                    icon
                    labelPosition="left"
                    className=""
                    as={NavLink} exact to='/view/programs'
                  >
                    <Icon name="arrow left" />
                    Atgal
                  </Button>
                  <Button
                    id='details'
                    type="submit"
                    className="controls"
                    primary
                    onClick={createProgram}
                    disabled={!formValid}
                  >
                    Sukurti
                  </Button>
                </div>
              </Form>
            </div>
          </Segment>
        </Grid.Column>

      </Grid>

    </div>
  );
}
