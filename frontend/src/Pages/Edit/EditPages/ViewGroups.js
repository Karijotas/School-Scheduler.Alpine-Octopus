import React, { useEffect, useState } from 'react'
import { Button, Dropdown, Icon, Input, Pagination, Table } from 'semantic-ui-react'
<<<<<<< HEAD
import { CreatePage } from '../../Create/CreatePage';
import { EditObject } from './EditObject';
=======
>>>>>>> origin/programs
import './ViewGroups.css';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewGroups() {

    const [active, setActive] = useState('')

    const [create, setCreate] = useState('')

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


    return (


        <div>
            {create && (<div>
                <CreatePage /></div>)}
            {active && (<div className='edit'>
                <EditObject id={active} /></div>)}


            {!active && !create && (

                <div id='groups'>
                    <Input placeholder='Filtruoti pagal pavadinimą' />
                    <Dropdown
                        button
                        className='icon'
                        floating
                        labeled
                        icon='angle down'
                        options={null}
                        search
                        text='Filtruoti pagal mokslo metus'
                    />
                    <Button>Filtruoti pagal programą</Button>

                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują grupę</Button>
                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Grupės pavadinimas "Teams"</Table.HeaderCell>
                                <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                                <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                                <Table.HeaderCell>Programa</Table.HeaderCell>
                                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {groups.map(group => (

                                <Table.Row key={group.id}>
                                    <Table.Cell>{group.name}</Table.Cell>
                                    <Table.Cell>{group.schoolYear}</Table.Cell>
                                    <Table.Cell>{group.studentAmount}</Table.Cell>
                                    <Table.Cell>{group.program}</Table.Cell>
                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(group.id)}></Button>
                                        <Button basic color='black' compact icon='trash alternate' onClick={() => removeGroup(group.id)}></Button>

                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>

                    </Table>

                    <Pagination
                        defaultActivePage={1}
                        firstItem={groups.firstItem}
                        lastItem={groups.lastItem}
                        pointing
                        totalPages={3}
                    />
                </div>
            )}

        </div>
    )
}


