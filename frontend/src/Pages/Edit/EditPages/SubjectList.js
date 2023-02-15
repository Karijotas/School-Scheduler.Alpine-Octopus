import React from "react";
import {
  Button,
  Table,
  Input,
  Icon,
  Pagination,
  ButtonGroup,
} from "semantic-ui-react";
import { useState, useEffect } from "react";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);

  const [activeItem, setActiveItem] = useState("");
  const [activePage, setActivePage] = useState(0);


  const fetchSubjects = async () => {
    fetch("/api/v1/subjects")
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
  };

  const removeSubject = (id) => {
    fetch("/api/v1/subjects/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchSubjects);
  };

  useEffect(() => {
    fetchSubjects();
  }, []);

  

  return (
    <div id="groups">
      <Input placeholder="Filtruoti pagal pavadinimą" />

      <Button
        icon
        labelPosition="left"
        primary
        href="#/create"
        className="controls"
      >
        <Icon name="database" />
        Kurti naują dalyką
      </Button>

      <Table selectable>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
            <Table.HeaderCell>Veiksmai</Table.HeaderCell>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {subjects.map((subject) => (
            <Table.Row key={subject.id}>
              <Table.Cell>{subject.name}</Table.Cell>
              <Table.Cell collapsing>
                <Button basic primary compact icon='eye' title='Peržiūrėti'></Button>
                <Button basic color='black' compact icon='trash alternate' onClick={() => removeSubject(subject.id)}></Button>

              </Table.Cell>
            </Table.Row>
          ))}
        </Table.Body>
      </Table>

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
