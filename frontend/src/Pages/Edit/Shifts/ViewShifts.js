import React from 'react';
import { NavLink } from 'react-router-dom';
import { Button, Divider, Grid, Icon, Input, Segment, Table } from 'semantic-ui-react';
import MainMenu from '../../../Components/MainMenu';
import { EditMenu } from '../../../Components/EditMenu';

export function ViewShifts() {

    return (


        <div >
            <MainMenu />

            <Grid columns={2} >
                <Grid.Column width={2} id='main'>
                    <EditMenu />
                </Grid.Column>

                <Grid.Column textAlign='left' verticalAlign='top' width={13}>
                    <Segment id='segment' color='teal'>

                        <div  >
                            <Input
                                title='Filtruoti'
                                className='controls1'
                                placeholder='Filtruoti '
                            />



                            <Button
                                title='Kurti naują pamainą'
                                icon
                                labelPosition='left'
                                primary
                                className='controls'
                                as={NavLink}
                                exact to='/create/shifts'>
                                <Icon name='database' />Kurti naują pamainą</Button>

                            <Divider horizontal hidden></Divider>
                        </div>


                        <Table selectable >
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Pamaina</Table.HeaderCell>
                                    <Table.HeaderCell>Laikas</Table.HeaderCell>
                                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                        </Table>
                    </Segment>
                </Grid.Column>

            </Grid>


        </div>
    )
}