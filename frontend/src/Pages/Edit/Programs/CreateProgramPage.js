import React, { useState } from "react";
import {
  Button,
  Form, Grid, Icon, Segment, TextArea
} from "semantic-ui-react";

import { NavLink, useHref } from 'react-router-dom';
import MainMenu from '../../../Components/MainMenu';
import { EditMenu } from '../../../Components/EditMenu';

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

  const listUrl = useHref('/view/programs');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectHours, setSubjectHours] = [];
  const [error, setError] = useState();
  const [hours, setHours] = useState("");

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };

    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };

  const createProgram = () => {
    fetch("/api/v1/programs", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(applyResult)
    .then(() => window.location = listUrl);
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
                  <input
                    placeholder="Programos pavadinimas"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                </Form.Field>
                <Form.Group widths="equal">
                  <Form.Field>
                    <label>Aprašymas</label>
                    <TextArea
                      placeholder="Aprašymas"
                      style={{ minHeight: 100 }}
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
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
                    type="submit"
                    className="controls"
                    primary
                    onClick={createProgram}
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
