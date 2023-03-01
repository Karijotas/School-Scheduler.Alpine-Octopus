import React, { useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateModulePage() {
  // const [create, setCreate] = useState()
  const listUrl = useHref('/view/modules');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [modules, setModules] = useState("");

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
    }).then(applyResult).then(() => window.location = listUrl);
  };

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
                  <input
                    placeholder="Modulio pavadinimas"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Aprašymas</label>
                  <Input
                    placeholder="Aprašymas"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
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
                    id="details"
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
