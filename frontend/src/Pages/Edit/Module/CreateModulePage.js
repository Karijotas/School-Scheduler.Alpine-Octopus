import React, { useState , useEffect } from "react";
import { NavLink } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateModulePage() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [modules, setModules] = useState("");

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [descriptionDirty, setDescriptionDirty] = useState(false);
  const [nameError, setNameError] = useState("The name field is required!")
  const [descriptionError, setDescriptionError] = useState("")
  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if(nameError || descriptionError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, descriptionError])
  

  const blurHandler = (e) => {
    switch (e.target.name){
      case 'name':
        setNameDirty(true);
        break
        case 'description':
          setDescriptionDirty(true);
          break
    }
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

  const createModule = () => {
    fetch("/api/v1/modules", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(applyResult);
  };

  // useEffect(() => {
  //   fetch("/api/v1/modules/")
  //     .then((response) => response.json())
  //     .then((data) =>
  //       setModules(
  //         data.map((x) => {
  //           return { key: x.id, text: x.name, value: x.id };
  //         })
  //       )
  //     );
  // }, []);

  const fetchModules = async () => {
    fetch(`/api/v1/modules/`)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
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
                  <label>Modulio pavadinimas</label>
                  {(nameDirty && nameError) && <div style={{color: "red"}}>{nameError}</div>}
                  <input 
                  name="name" 
                  onBlur={blurHandler}
                    placeholder="Modulio pavadinimas"
                    value={name}
                    onChange={e => nameHandler(e)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Aprašymas</label>
                  {(descriptionError) && <div style={{color: "red"}}>{descriptionError}</div>}
                  <Input
                  name="description" 
                  onBlur={blurHandler}
                    placeholder="Aprašymas"
                    value={description}
                    onChange={(e) => descriptionHandler(e)}
                  />
                </Form.Field>

                <div>
                  <Button
                    icon
                    labelPosition="left"
                    className=""
                    as={NavLink} exact to='/view/modules'
                  >
                    <Icon name="arrow left" />
                    Atgal
                  </Button>
                  <Button
                    type="submit"
                    className="controls"
                    primary
                    onClick={createModule}
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