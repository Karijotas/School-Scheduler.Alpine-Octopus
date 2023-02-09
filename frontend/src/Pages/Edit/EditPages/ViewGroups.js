import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import { Button, Input, Pagination, Table } from 'semantic-ui-react'
import './ViewGroups.css';

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

    const removeGroup = (id) => {
        fetch('/api/v1/groups/' + id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        }).then(fetchGroups);
    }


    useEffect(() => {
        fetchGroups();
    }, []);


    return (<div className='groups'>
        <Input placeholder='Filtruoti' className='controls'/>

        <Table basic='very'>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>ID</Table.HeaderCell>
                    <Table.HeaderCell>Grupės pavadinimas "Teams"</Table.HeaderCell>
                    <Table.HeaderCell>Mokslo Metai</Table.HeaderCell>
                    <Table.HeaderCell>Studentų Kiekis</Table.HeaderCell>
                    <Table.HeaderCell>Programa</Table.HeaderCell>                    
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
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
                        <Table.Cell>{group.modifiedDate}</Table.Cell>
                        <Table.Cell collapsing>
                        <Link to={'/groups/edit/' + group.id}>
                             <Button basic circular compact icon='pencil' ></Button>
                            </Link> 
                            <Button basic circular negative compact icon='trash alternate' onClick={() => removeGroup(group.id)}></Button>

                        </Table.Cell>
                    </Table.Row>
                ))}


            </Table.Body>
           
        </Table>

        <Pagination 
            defaultActivePage={1}
            firstItem={null}
            lastItem={null}
            secondary
            pointing
            totalPages={3}
        />
    </div>
    )
}


