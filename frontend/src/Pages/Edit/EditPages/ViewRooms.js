import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import { Button, Dropdown, Icon, Input, Pagination, Table } from 'semantic-ui-react'
import { CreateRoom } from '../../Create/CreateRoom';
import { EditRoom } from './EditRoom';



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewRooms() {
    const [active, setActive] = useState('')

    const [create, setCreate] = useState('')

    

    const [rooms, setRooms] = useState([]);

    const fetchRooms = async () => {
        fetch('/api/v1/rooms')
            .then(response => response.json())
            .then(jsonResponse => setRooms(jsonResponse));
    };

    const removeRoom = (id) => {
        fetch('/api/v1/rooms/' + id, {
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
                <CreateRoom /></div>)}
            {active && (<div className='edit'>
                <EditRoom id={active} /></div>)}


            {!active && !create && (

                <div id='rooms'>
                    <Input placeholder='Filtruoti pagal klase' />
                    <Dropdown
                        button
                        className='icon'
                        floating
                        labeled
                        icon='angle down'
                        options={null}
                        search
                        text='Filtruoti pagal klase'
                    />
                    <Button>Filtruoti pagal klase</Button>

                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują grupę</Button>
                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Klases pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Pastatas</Table.HeaderCell>
                                <Table.HeaderCell>Aprasymas</Table.HeaderCell>
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
                                        <Button basic color='black' compact icon='trash alternate' onClick={() => removeRoom(room.id)}></Button>

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