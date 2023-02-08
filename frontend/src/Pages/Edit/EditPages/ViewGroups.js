import React, { useEffect, useState } from 'react'
import { Table } from 'semantic-ui-react'
import EditObjectModal from '../EditObject'

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewGroups() {
    const [groups, setGroups] = useState([]);

    const fetchGroups = async () => {
        fetch('/api/v1/groups')
            .then(response => response.json())
            .then(jsonResponse => setGroups(jsonResponse));
    };

    useEffect(() => {
        fetchGroups();
    }, []);


    return (<div>
        <Table celled>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>ID</Table.HeaderCell>
                    <Table.HeaderCell>Grupės pavadinimas "Teams"</Table.HeaderCell>
                    <Table.HeaderCell>Mokslo Metai</Table.HeaderCell>
                    <Table.HeaderCell>Studentų Kiekis</Table.HeaderCell>
                    <Table.HeaderCell>Programa</Table.HeaderCell>
                    <Table.HeaderCell>Pamaina</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
                {groups.map(group => (
                    <Table.Row key={group.id}>
                    <Table.Cell collapsing>{group.id}</Table.Cell>
                    <Table.Cell>{group.name}</Table.Cell>
                    <Table.Cell>{group.schoolYear}</Table.Cell>
                    <Table.Cell>{group.studentAmount}</Table.Cell>
                    <Table.Cell>{group.program}</Table.Cell>
                    <Table.Cell>{group.shift}</Table.Cell>
                    <Table.Cell collapsing>
                        <EditObjectModal />
                    </Table.Cell>
                </Table.Row>
                ))}
               
            </Table.Body>
        </Table>
    </div>
    )
}

