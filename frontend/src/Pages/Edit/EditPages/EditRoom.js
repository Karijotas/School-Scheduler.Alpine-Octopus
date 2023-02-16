import React, { useEffect, useState } from 'react'
import { Button, Icon, Input, Select, Table } from 'semantic-ui-react';
import { ViewRooms } from './ViewRooms';



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function EditRoom(props) {


    const [hide, setHide] = useState(false)

    const [active, setActive] = useState(true)

    const [error, setError] = useState();


    const [rooms, setRooms] = useState({
        name: '',
        building: '',
        description: '',
        modifiedDate: '',
    });

    useEffect(() => {
        fetch('/api/v1/rooms/' + props.id)
            .then(response => response.json())
            .then(setRooms);
    }, [props]);




    const applyResult = () => {

        setHide(true)

    }

    const updateRooms = () => {
        fetch('/api/v1/rooms/' + props.id, {
            method: 'PUT',
            headers: JSON_HEADERS,
            body: JSON.stringify(rooms)
        }).then(result => {
            if (!result.ok) {
                setError('Update failed');
            } else {
                setError();
            }
        }).then(applyResult)
    };

    const updateProperty = (property, event) => {
        setRooms({
            ...rooms,
            [property]: event.target.value
        });
    };

    const editThis = () => {
        setActive(false);
    }
    // const removeGroup = (id) => {
    //     fetch('/api/v1/groups/' + params.id, {
    //         method: 'DELETE',
    //         headers: JSON_HEADERS
    //     })
    //     .then(() => window.location = listUrl);
    // }


    return (<div>{active && !hide &&(<div >

        <Table celled color='violet'>
            <Table.Header >
                <Table.Row  >
                    <Table.HeaderCell >Klasės pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Pastatas</Table.HeaderCell>
                    <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
                <Table.Row  >
                    <Table.Cell >{rooms.name}</Table.Cell>
                    <Table.Cell >{rooms.building}</Table.Cell>
                    <Table.Cell >{rooms.description}</Table.Cell>

                    <Table.Cell collapsing > {rooms.modifiedDate}  </Table.Cell>

                    <Table.Cell collapsing ><Button onClick={editThis}>Taisyti</Button>
                    </Table.Cell>


                </Table.Row>

            </ Table.Body >
        </Table>
         <Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal</Button>
    </div>


    )}
        {!active && !hide && (<div >

            <Table celled color='violet'>
                <Table.Header >
                    <Table.Row  >
                        <Table.HeaderCell >Klasės pavadinimas</Table.HeaderCell>
                        <Table.HeaderCell>Pastatas</Table.HeaderCell>
                        <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                        <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                        <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                    </Table.Row>
                </Table.Header>

                <Table.Body>
                    <Table.Row  >
                        <Table.Cell collapsing><Input value={rooms.name} onChange={(e) => updateProperty('name', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input value={rooms.building} onChange={(e) => updateProperty('building', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input value={rooms.description} onChange={(e) => updateProperty('description', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing> {rooms.modifiedDate}  </Table.Cell>

                        <Table.Cell collapsing ><Button onClick={() => setActive(true)}>Atšaukti</Button><Button primary onClick={updateRooms}>Atnaujinti</Button>
                        </Table.Cell>

                        
                        
                    </Table.Row>
                    
                </ Table.Body >
                
            </Table>
<Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left"/>Atgal</Button>
        </div>)}

        {hide && <div><ViewRooms /></div>}



    </div>
    )
}

