import React, { useEffect, useState } from "react";
import {
  Button,
  Divider,
  Icon,
  Input,
  Table,
  Grid,
  Segment,
  List,
  Form,
  Select,
} from "semantic-ui-react";
import { ViewShifts } from "./ViewShifts";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from '../../../Components/EditMenu';
import { useParams } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditShiftObject() {
  const params = useParams();
  

  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [shifts, setShifts] = useState({
    name: "",
    starts: "",
    ends: "",
  });

  useEffect(() => {
    fetch("/api/v1/shifts/" + params.id)
      .then((response) => response.json())
      .then(setShifts);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const updateShifts = () => {
    fetch("/api/v1/shifts/" + params.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(shifts),
    })
      .then((result) => {
        if (!result.ok) {
          setError("Update failed");
        } else {
          setError();
        }
      })
      .then(applyResult);
  };

  const updateProperty = (property, event) => {
    setShifts({
      ...shifts,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };



  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="shifts" />
        </Grid.Column>
        <Grid.Column
          floated="left"
          textAlign="left"
          verticalAlign="top"
          width={13}
        >
          <Segment raised color="teal">
            {active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                    <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos nuo:</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos iki:</Table.HeaderCell>
                      <Table.HeaderCell>Redagavimo data</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{shifts.name}</Table.Cell>
                      <Table.Cell>{shifts.starts}</Table.Cell>
                      <Table.Cell>{shifts.ends}</Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {shifts.modifiedDate}{" "}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Button onClick={editThis}>Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                
                <Divider hidden />
                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/shifts"
                >
                  <Icon name="arrow left" />
                  Atgal
                </Button>
              </div>
            )}
            {!active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                    <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos nuo:</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos iki:</Table.HeaderCell>
                      <Table.HeaderCell>Redagavimo data</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input
                          value={shifts.name}
                          onChange={(e) => updateProperty("name", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={shifts.starts} 
                          onChange={(e) => updateProperty("starts", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={shifts.ends} 
                          onChange={(e) => updateProperty("ends", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {shifts.modifiedDate}{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden></Divider>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button floated="right" primary onClick={updateShifts}>
                  Atnaujinti
                </Button>
              </div>
            )}
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
