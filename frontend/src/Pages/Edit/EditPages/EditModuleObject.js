import React, { useEffect, useState } from "react";
import { Button, Icon, Input, Table } from "semantic-ui-react";
import { ViewModules } from "./ViewModules";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditModuleObject(props) {
  const [hide, setHide] = useState(false);

  const [active, setActive] = useState(true);

  const [error, setError] = useState();
  const [subjectsInModule, setSubjectsInModule] = useState([]);
  const [moduleId, setModuleId] = useState("");

  const [modules, setModules] = useState({
    name: "",
    description: "",
    createdDate: "",
    modifiedDate: "",
  });

  const fetchSubjectsInModule = async () => {
    fetch(`/api/v1/modules/${props.id}/subjects`)
      .then((response) => response.json())
      .then((jsonResponse) => setSubjectsInModule(jsonResponse));
  };

  useEffect(() => {
    fetch("/api/v1/modules/" + props.id)
      .then((response) => response.json())
      .then(setModules);
  }, [props]);

  const applyResult = () => {
    setHide(true);
  };

  const updateModules = () => {
    fetch("/api/v1/modules/update/" + props.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(modules),
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
    setModules({
      ...modules,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };

  return (
    <div>
      {active && !hide && (
        <div>
          <Table celled color="violet">
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              <Table.Row>
                <Table.Cell>{modules.name}</Table.Cell>
                <Table.Cell>{modules.description}</Table.Cell>
                <Table.Cell collapsing> {modules.modifiedDate} </Table.Cell>
                <Table.Cell collapsing>
                  <Button onClick={editThis}>Redaguoti</Button>
                </Table.Cell>
              </Table.Row>
            </Table.Body>
          </Table>

          {/* <Divider hidden />
          <Table>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell width={6}>Modulio dalykai</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {subjectsInModule.map((subject) => (
                <Table.Row key={subject.id}>
                  <Table.Cell>{subject.subject.name}</Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table> */}

          <Button
            icon
            labelPosition="left"
            className=""
            onClick={() => setHide(true)}
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
                <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              <Table.Row>
                <Table.Cell collapsing>
                  <Input
                    value={modules.name}
                    onChange={(e) => updateProperty("name", e)}
                  />
                </Table.Cell>
                <Table.Cell collapsing>
                  <Input
                    placeholder={modules.description}
                    onChange={(e) => updateProperty("description", e)}
                  />
                </Table.Cell>
                <Table.Cell collapsing> {modules.modifiedDate} </Table.Cell>

                <Table.Cell collapsing>
                  <Button onClick={() => setHide(true)}>Atšaukti</Button>
                  <Button primary onClick={updateModules}>
                    Atnaujinti
                  </Button>
                </Table.Cell>
              </Table.Row>
            </Table.Body>
          </Table>
          
        </div>
      )}

      {hide && (
        <div>
          <ViewModules />
        </div>
      )}
    </div>
  );
}
