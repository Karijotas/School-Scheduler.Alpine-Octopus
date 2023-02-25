import React, { useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateShiftPage() {
  // const [create, setCreate] = useState()
  const listUrl = useHref('/view/shifts');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [starts, setStarts] = useState("");
  const [ends, setEnds] = useState("");
  const [shifts, setShifts] = useState("");

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

  const createShift = () => {
    fetch("/api/v1/shifts", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        starts,
        ends,
      }),
    }).then(applyResult).then(() => window.location = listUrl);
  };

  const fetchShifts = async () => {
    fetch(`/api/v1/shifts/`)
      .then((response) => response.json())
      .then((jsonRespones) => setShifts(jsonRespones));
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
                  <label>Pamainos pavadinimas</label>
                  <input
                    placeholder="Pamainos pavadinimas"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Pamokos nuo:</label>
                  <Input
                    placeholder="Pamokos nuo:"
                    value={starts}
                    onChange={(e) => setStarts(e.target.value)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Pamokos iki:</label>
                  <Input
                    placeholder="Pamokos iki:"
                    value={ends}
                    onChange={(e) => setEnds(e.target.value)}
                  />
                </Form.Field>

                <div>
                  <Button
                    icon
                    labelPosition="left"
                    className=""
                    as={NavLink} exact to='/view/shifts'
                  >
                    <Icon name="arrow left" />
                    Atgal
                  </Button>
                  <Button
                    type="submit"
                    className="controls"
                    primary
                    onClick={createShift}
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
