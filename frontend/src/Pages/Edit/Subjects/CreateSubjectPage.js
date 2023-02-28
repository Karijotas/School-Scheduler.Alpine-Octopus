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

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  
  const [descriptionDirty, setDescriptionDirty] = useState(false);
  const [nameError, setNameError] = useState("The name field is required!")
  
  const [descriptionError, setDescriptionError] = useState("")
  const [formValid, setFormValid] = useState(false)


  const [selectErrorModule, setSelectErrorModule] = useState("Required")
   const [selectErrorTeachers, setSelectErrorTeachers] = useState("Required")
   const [selectErrorRooms, setSelectErrorRooms] = useState("Required")

  

  useEffect(() => {
    if(nameError || descriptionError || selectErrorModule || selectErrorTeachers || selectErrorRooms) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, descriptionError, selectErrorModule, selectErrorTeachers, selectErrorRooms])
  

  const blurHandler = (e) => {
    switch (e.target.name){
      case 'name':
        setNameDirty(true);
        break
    }
  }
  const selectModuleHandler = () => {
    setSelectErrorModule("")
  }
  const selectTeachersHandler = () => {
    setSelectErrorTeachers("")
  }
  const selectRoomsHandler = () => {
    setSelectErrorRooms("")
  }

  const nameHandler = (e) => {
    setName(e.target.value)
    if(e.target.value.length <2 || e.target.value.length > 40){
      setNameError("Size must be between 2 and 40 characters!")
      if(!e.target.value){
        setNameError("The name field is required!")
      }
    } else {
      setNameError("")
    }
  }

  const descriptionHandler = (e) => {
    setDescription(e.target.value)
    if(e.target.value.length > 100){
      setDescriptionError("Size must be less than 100 characters!")
    } else {
      setDescriptionError("")
    }
  }

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
              {(nameDirty && nameError) && <div style={{color: "red"}}>{nameError}</div>}
              <input
                placeholder="Dalyko pavadinimas"
                value={name}
                onChange={(e) => nameHandler(e)}
                onBlur={blurHandler}
                name="name"
              />
            </Form.Field>
            <Form.Field>
              <label>Aprašymas</label>
              {(descriptionError) && <div style={{color: "red"}}>{descriptionError}</div>}
              <input
                placeholder="Aprašymas"
                value={description}
                onChange={(e) => descriptionHandler(e)}
                name="description"
                onBlur={blurHandler}
              />
            </Form.Field>
            <Form.Group widths="equal">
              <Form.Field>
                <label>Moduliai</label> {(selectErrorModule) && <div style={{color: "red"}}>{selectErrorModule}</div>}
                <Select
                name = "module"
                  options={modules}
                  placeholder="Moduliai"
                  onClose={() => console.log(moduleId)}
                  onChange={((e, data) => setModuleId(data.value), selectModuleHandler)}
                />
              </Form.Field>
              <Form.Field>
                <label>Mokytojai</label> {(selectErrorTeachers) && <div style={{color: "red"}}>{selectErrorTeachers}</div>}
                <Select
                name = "teachers"
                  options={teachers}
                  placeholder="Mokytojai"
                  onClose={() => console.log(teacherId)}
                  onChange={((e, data) => setTeacherId(data.value),selectTeachersHandler)}
                />
              </Form.Field>
              <Form.Field>
                <label>Klasės</label> {(selectErrorRooms) && <div style={{color: "red"}}>{selectErrorRooms}</div>}
                <Select
                name = "rooms"
                  options={rooms}
                  placeholder="Klasės"
                  onClose={() => console.log(roomId)}
                  onChange={((e, data) => setRoomId(data.value),selectRoomsHandler)}
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
              disabled={!formValid}
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