import React from 'react'
import { Button, Grid, Table } from 'semantic-ui-react'
import EditObjectModal from '../EditObject'


function ViewPrograms() {



    return (<div>
        <Table celled>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>ID</Table.HeaderCell>
                    <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Aprašas</Table.HeaderCell>                    
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                <Table.Row >
                    <Table.Cell collapsing>1</Table.Cell>
                    <Table.Cell>John</Table.Cell>
                    <Table.Cell>No Action</Table.Cell>                   
                    <Table.Cell collapsing>
                        <Grid>
                            <Grid.Row columns={3}>
                        <Grid.Column><Button primary icon='eye' basic></Button></Grid.Column>
                    <Grid.Column><EditObjectModal /> </Grid.Column>                      
                    <Grid.Column><Button basic icon='remove' negative></Button></Grid.Column>
                        </Grid.Row>
                        </Grid>
                    </Table.Cell>
                </Table.Row>
                <Table.Row>
                    <Table.Cell>2</Table.Cell>
                    <Table.Cell>Jamie</Table.Cell>
                    <Table.Cell>Approved</Table.Cell>                   
                    <Table.Cell >
                    <Button icon='eye' basic></Button>
                        <EditObjectModal />
                    </Table.Cell>
                </Table.Row>
                <Table.Row>
                    <Table.Cell>3</Table.Cell>
                    <Table.Cell>Jill</Table.Cell>
                    <Table.Cell>Denied</Table.Cell>           
                    <Table.Cell>
                    <Button icon='eye' basic></Button>
                        <EditObjectModal />
                    </Table.Cell>
                </Table.Row>
                <Table.Row >
                    <Table.Cell>4</Table.Cell>
                    <Table.Cell>John</Table.Cell>
                    <Table.Cell>No Action</Table.Cell>                 
                    <Table.Cell>
                    <Button icon='eye' basic></Button>
                        <EditObjectModal />
                    </Table.Cell>
                </Table.Row>
                <Table.Row>
                    <Table.Cell>5</Table.Cell>
                    <Table.Cell>Jamie</Table.Cell>
                    <Table.Cell >Approved</Table.Cell>              
                    <Table.Cell>
                    <Button icon='eye' basic></Button>
                        <EditObjectModal />
                    </Table.Cell>
                </Table.Row>
                <Table.Row>
                    <Table.Cell>6</Table.Cell>
                    <Table.Cell>Jill</Table.Cell>
                    <Table.Cell >Denied</Table.Cell>                   
                    <Table.Cell>
                    <Button icon='eye' basic></Button>
                        <EditObjectModal />
                    </Table.Cell>
                </Table.Row>
            </Table.Body>
        </Table>
    </div>
    )
}

export default ViewPrograms