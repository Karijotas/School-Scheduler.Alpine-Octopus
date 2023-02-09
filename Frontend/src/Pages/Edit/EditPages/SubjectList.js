import React from 'react'
import { Table } from 'semantic-ui-react'
import EditObjectModal from '../EditObject'
import { useState, useEffect } from 'react';
// import { EditSubject } from '../EditSubject';

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


    return (<div>
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
                            {/* <EditSubject /> */}
                        </Table.Cell>
                </Table.Row>
                 ))}
            </Table.Body>
        </Table>
    </div>
    )
}