import React from 'react'
import { Button, Table } from 'semantic-ui-react'
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { EditSubject } from '../EditSubject';

export function SubjectList() {

    const [subjects, setSubjects] = useState([]);


    const fetchSubjects = async () => {
        fetch('/api/v1/subjects')
          .then((response) => response.json())
          .then((jsonResponse) => setSubjects(jsonResponse));
          };

useEffect(() => {
  fetchSubjects();
}, []);


    return (
        <Table celled>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>ID</Table.HeaderCell>
                    <Table.HeaderCell>Pavadinimas</Table.HeaderCell>                    
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
            {subjects.map((subject) => (
                <Table.Row key={subject.id}>
                        <Table.Cell collapsing>{subject.id}</Table.Cell>
                        <Table.Cell>{subject.name}</Table.Cell>
                        <Table.Cell collapsing> 
                        {/* <Button> Hello<EditOBJ /></Button> */}
                            {/* <EditObjectModal /> */}
                            <Link to={"/subjects/update/" + subject.id}>
                            <Button icon='pencil alternate' basic ></Button>
                      </Link>
                      <Link to={"/subjects/delete/" + subject.id}>
                      <Button basic icon='remove' negative></Button>
                      </Link>
                            
                            
                        </Table.Cell>
                </Table.Row>
                 ))}
            </Table.Body>
        </Table>
    
    )
}