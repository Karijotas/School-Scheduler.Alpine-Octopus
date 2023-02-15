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

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);

  const [activeItem, setActiveItem] = useState("");

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
