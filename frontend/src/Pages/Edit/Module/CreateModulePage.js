import React, { useState, useEffect } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateModulePage() {
  // const [create, setCreate] = useState()
  const listUrl = useHref('/view/modules/edit/');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [modules, setModules] = useState("");

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

  const createModule = () => {
    fetch("/scheduler/api/v1/modules", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(applyResult);
  };

  const fetchModules = async () => {
    fetch(`/scheduler/api/v1/modules/`)
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
                  {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
                  <input
                    placeholder="Modulio pavadinimas"
                    name="name"
                    value={name}
                    onBlur={blurHandler}
                    onChange={e => nameHandler(e)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Aprašymas</label>
                  {(descriptionError) && <div style={{ color: "red" }}>{descriptionError}</div>}
                  <Input
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
                    id='details'
                    className="controls"
                    primary
                    disabled={!formValid}
                    onClick={createModule}
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
