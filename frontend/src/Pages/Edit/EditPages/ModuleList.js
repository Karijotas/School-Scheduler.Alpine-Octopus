import React from "react";
import {
  Button,
  Table,
  Input,
  Dropdown,
  Icon,
  Pagination,
} from "semantic-ui-react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ModuleList() {
  const [modules, setModules] = useState([]);

  const [activeItem, setActiveItem] = useState("");

  const fetchModules = async () => {
    fetch("/api/v1/modules")
      .then((response) => response.json())
      .then((jsonResponse) => setModules(jsonResponse));
  };

  const removeModule = (id) => {
    fetch("/api/v1/modules/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchModules);
  };

  useEffect(() => {
    fetchModules();
  }, []);

  return (
    <div id="modules">
      <Input placeholder="Filtruoti pagal pavadinimą" />
      <Button
        icon
        labelPosition="left"
        primary
        href="#/create"
        className="controls"
      >
        <Icon name="database" />
        Kurti naują modulį
      </Button>

      <Table selectable>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
            <Table.HeaderCell>Veiksmai</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {modules.map((module) => (
            <Table.Row key={module.id}>
              <Table.Cell>{module.name}</Table.Cell>
              <Table.Cell collapsing>
                             <Button basic primary compact icon='eye' title='Peržiūrėti' active={activeItem === modules.id} onClick={console.log('modules/' +module.id)}></Button>
                            <Button basic color='black' compact icon='trash alternate' onClick={() => removeModule(module.id)}></Button>

                        </Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>

      <Pagination
    defaultActivePage={1}
    ellipsisItem={{ content: <Icon name='ellipsis horizontal' />, icon: true }}
    firstItem={{ content: <Icon name='angle double left' />, icon: true }}
    lastItem={{ content: <Icon name='angle double right' />, icon: true }}
    prevItem={{ content: <Icon name='angle left' />, icon: true }}
    nextItem={{ content: <Icon name='angle right' />, icon: true }}
    totalPages={3}
  />
    </div>
  );
}
