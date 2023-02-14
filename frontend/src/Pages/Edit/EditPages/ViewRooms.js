import React, { useEffect, useState } from 'react'
import { Button, Dropdown, Icon, Input, Pagination, Table } from 'semantic-ui-react'
import { CreatePage } from '../../Create/CreatePage';
import { EditObject } from './EditObject';
import './ViewGroups.css';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewRooms() {

    const [active, setActive] = useState('')

    const [create, setCreate] = useState('')

    const [rooms, setRooms] = useState([]);

    const fetchRooms = async () => {
        fetch('/rooms')
            .then(response => response.json())
            .then(jsonResponse => setRooms(jsonResponse));
    };

    const removeGroup = (id) => {
        fetch('/rooms/' + id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        }).then(fetchRooms);
    }


    useEffect(() => {
        fetchRooms();
    }, []);


    return (


        <div>
            {create && (<div>
                <CreatePage /></div>)}
            {active && (<div className='edit'>
                <EditObject id={active} /><Button icon labelPosition='left' className='controls' setActive='rooms' onClick={() => setActive('')}>
                    <Icon name='arrow left' />Atgal</Button></div>)}


            {!active && !create && (

                <div id='rooms'>
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

                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują Klasę</Button>
                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Klasės pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Pastatas</Table.HeaderCell>
                                <Table.HeaderCell>Aprasas</Table.HeaderCell>
                                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {rooms.map(room => (

                                <Table.Row key={room.id}>
                                    <Table.Cell>{room.name}</Table.Cell>
                                    <Table.Cell>{room.building}</Table.Cell>
                                    <Table.Cell>{room.description}</Table.Cell>
                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(room.id)}></Button>
                                        <Button basic color='black' compact icon='trash alternate' onClick={() => removeGroup(room.id)}></Button>

                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>

                    </Table>

                    <Pagination
                        defaultActivePage={1}
                        firstItem={rooms.firstItem}
                        lastItem={rooms.lastItem}
                        pointing
                        totalPages={3}
                    />
                </div>
            )}

        </div>
    )
}


