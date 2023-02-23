import React, { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import { Button, Form, Grid, Icon, Segment, Select } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateSubjecPage() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [modules, setModules] = useState([]);
  const [moduleId, setModuleId] = useState();
  const [teachers, setTeachers] = useState([]);
  const [teacherId, setTeacherId] = useState();
  const [rooms, setRooms] = useState([]);
  const [roomId, setRoomId] = useState();

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

  const createSubject = () => {
    fetch("/api/v1/subjects?moduleId=" + moduleId, {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
        modules,
        teachers,
        rooms,
      }),
    }).then(applyResult);
  };
  useEffect(() => {
    fetch("/api/v1/modules/")
      .then((response) => response.json())
      .then((data) =>
        setModules(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  useEffect(() => {
    fetch("/api/v1/teachers/")
      .then((response) => response.json())
      .then((data) =>
        setTeachers(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  useEffect(() => {
    fetch("/api/v1/rooms/")
      .then((response) => response.json())
      .then((data) =>
        setRooms(
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
              <label>Dalyko pavadinimas</label>
              <input
                placeholder="Dalyko pavadinimas"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Form.Field>
            <Form.Field>
              <label>Aprašymas</label>
              <input
                placeholder="Aprašymas"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </Form.Field>
            <Form.Group widths="equal">
              <Form.Field>
                <label>Moduliai</label>
                <Select
                  options={modules}
                  placeholder="Moduliai"
                  onClose={() => console.log(moduleId)}
                  onChange={(e, data) => setModuleId(data.value)}
                />
              </Form.Field>
              <Form.Field>
                <label>Mokytojai</label>
                <Select
                  options={teachers}
                  placeholder="Mokytojai"
                  onClose={() => console.log(teacherId)}
                  onChange={(e, data) => setTeacherId(data.value)}
                />
              </Form.Field>
              <Form.Field>
                <label>Klasės</label>
                <Select
                  options={rooms}
                  placeholder="Klasės"
                  onClose={() => console.log(roomId)}
                  onChange={(e, data) => setRoomId(data.value)}
                />
              </Form.Field>
            </Form.Group>
            <div>
              <Button
                icon
                labelPosition="left"
                className=""
                as={NavLink} exact to='/view/subjects'
              >
                <Icon name="arrow left" />
                Atgal
              </Button>
              <Button
                type="submit"
                className="controls"
                primary
                onClick={createSubject}
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
