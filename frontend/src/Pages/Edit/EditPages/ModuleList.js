import React from "react";
import {
  Button,
  Table,
  Input,
  Icon,
  Pagination,
  ButtonGroup,
  Divider,
} from "semantic-ui-react";
import { useState, useEffect } from "react";
import { CreateModule } from "../../Create/CreateModule";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ModuleList() {
  const [modules, setModules] = useState([]);

  const [activeItem, setActiveItem] = useState("");
  const [activePage, setActivePage] = useState(0);


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
        href="#/CreateNewModule"
        className="controls"
        onClick={CreateModule}
      >
        <Icon name="database" />
        Kurti naują modulį
      </Button>
      <Divider hidden></Divider>


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
      <Divider hidden></Divider>

      <ButtonGroup basic compact>
            <Button onClick={() => setActivePage(activePage <= 0 ? activePage : activePage -1)} icon><Icon name="arrow left" />  </Button>
            <Button onClick={() => setActivePage(0)}> 1 </Button>
            <Button onClick={() => setActivePage(1)}> 2 </Button>
            <Button onClick={() => setActivePage(2)}> 3 </Button>
            <Button onClick={() => setActivePage(activePage + 1)} icon><Icon name="arrow right" />  </Button>
          </ButtonGroup>
    </div>
  );
}
