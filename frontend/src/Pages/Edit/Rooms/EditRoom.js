import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Button, Grid, Icon, Input, Segment, Table } from 'semantic-ui-react';
import MainMenu from '../../../Components/MainMenu';
import { EditMenu } from '../../../Components/EditMenu';



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function EditRoom() {

    const params = useParams();

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
        fetch('/api/v1/rooms/' + params.id)
            .then(response => response.json())
            .then(setRooms);
    }, []);




    const applyResult = () => {

        setHide(true)


    }

    const updateRooms = () => {
        fetch('/api/v1/rooms/' + params.id, {
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




    return (<div><MainMenu />

        <Grid columns={2} >
            <Grid.Column width={2} id='main'>
                <EditMenu />
            </Grid.Column>

            <Grid.Column textAlign='left' verticalAlign='top' width={13}>
                <Segment id='segment' color='teal'>{active && !hide && (<div >

                    <Table celled>
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

                                <Table.Cell collapsing ><Button onClick={editThis} id='details'>Redaguoti</Button>
                                </Table.Cell>


                            </Table.Row>

                        </ Table.Body >
                    </Table>
                    <Button icon labelPosition="left" className="" href='#/view/rooms'><Icon name="arrow left" />Atgal</Button>
                </div>


                )}
                    {!active && !hide && (<div >

                        <Table celled>
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

                                    <Table.Cell collapsing >
                                        <Button onClick={() => setActive(true)}>Atšaukti</Button>
                                        <Button primary onClick={updateRooms} id='details'>Atnaujinti</Button>
                                    </Table.Cell>



                                </Table.Row>

                            </ Table.Body >

                        </Table>

                    </div>)}
                </Segment>
            </Grid.Column>

        </Grid>

    </div>
    )
}