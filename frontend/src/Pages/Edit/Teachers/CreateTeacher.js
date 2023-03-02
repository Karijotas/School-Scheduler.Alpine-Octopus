import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Segment, Select } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateTeacher() {
  // const [create, setCreate] = useState()
  const listUrl = useHref('/view/teachers');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [loginEmail, setLoginEmail] = useState("");
  const [contactEmail, setContactEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [workHoursPerWeek, setWorkHoursPerWeek] = useState("");
  const [subjectId, setSubjectId] = useState("");
  const [subjects, setSubjects] = useState([]);


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

  const createTeacher = () => {
    fetch("/api/v1/teachers?subjectId=" + subjectId, {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        loginEmail,
        contactEmail,
        phone,
        workHoursPerWeek,
        
      }),
    }).then(applyResult).then(() => window.location = listUrl);
  };

  useEffect(() => {
    fetch("/api/v1/subjects/")
      .then((response) => response.json())
      .then((data) =>
        setSubjects(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

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
              <label>Vardas ir pavardė</label>
              <input
                placeholder="Vardas ir pavardė"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Form.Field>
            <Form.Field>
              <label>Teams vartotojo vardas</label>
              <input
                placeholder="Teams vartotojo vardas"
                value={loginEmail}
                onChange={(e) => setLoginEmail(e.target.value)}
              />
            </Form.Field>
            <Form.Field>
              <label>El. paštas</label>
              <input
                placeholder="El. paštas"
                value={contactEmail}
                onChange={(e) => setContactEmail(e.target.value)}
              />
            </Form.Field>
            <Form.Field>
              <label>Telefono nr.</label>
              <input
                placeholder="Telefono nr."
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
              />
            </Form.Field>
            <Form.Field>
              <label>Užimtumas (val. per savaitę)</label>
              <input
                placeholder="Užimtumas (val. per savaitę)"
                value={workHoursPerWeek}
                onChange={(e) => setWorkHoursPerWeek(e.target.value)}
              />
            </Form.Field>

            <div>
              <Button
                icon
                labelPosition="left"
                className=""
                as={NavLink} exact to='/view/teachers'
              >
                <Icon name="arrow left" />
                Atgal
              </Button>
              <Button
                type="submit"
                className="controls"
                primary
                
                onClick={createTeacher}
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
