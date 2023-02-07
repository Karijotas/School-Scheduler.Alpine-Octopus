import React from 'react'
import { Table } from 'semantic-ui-react'
import EditObjectModal from './EditObject'

// Here we should pass the selection as props, and then map the values from Backend to the tables
// I've just done here a simple if else that is only for example, not how it should work.
// Change/Edit this section to suit your case. 
// Best is to create a new file for your implementation based on this one, and later we will refactor and merge the best options

function ObjectList(props) {
    if (props === 'groups') {
        return (
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
                    <Table.Row >
                        <Table.Cell collapsing>1</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>No Action</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell collapsing>
                            <EditObjectModal />
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row>
                        <Table.Cell>2</Table.Cell>
                        <Table.Cell>Jamie</Table.Cell>
                        <Table.Cell>Approved</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell >
                            <EditObjectModal />
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row>
                        <Table.Cell>3</Table.Cell>
                        <Table.Cell>Jill</Table.Cell>
                        <Table.Cell>Denied</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>
                            <EditObjectModal />
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row >
                        <Table.Cell>4</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>No Action</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>
                            <EditObjectModal />
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row>
                        <Table.Cell>5</Table.Cell>
                        <Table.Cell>Jamie</Table.Cell>
                        <Table.Cell >Approved</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>
                            <EditObjectModal />
                        </Table.Cell>
                    </Table.Row>
                    <Table.Row>
                        <Table.Cell>6</Table.Cell>
                        <Table.Cell>Jill</Table.Cell>
                        <Table.Cell >Denied</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>John</Table.Cell>
                        <Table.Cell>
                            <EditObjectModal />
                        </Table.Cell>
                    </Table.Row>
                </Table.Body>
            </Table>
        )
    }
    if (props === 'subjects') {
        return (
            <p>
                Dalykai:
            </p>

        )
    }
    if (props === 'teachers') {
        return (
            <p>
                Mokytojai:
            </p>

        )
    }
    if (props === 'shifts') {
        return (
            <p>
                Pamainos:
            </p>

        )
    }
    if (props === 'rooms') {
        return (
            <p>
                Klasės:
            </p>

        )
    } 
    if (props === 'program') {
        return (
            <p>
                Programos:
            </p>

        )
    }
    if (props === 'modules'){
        return (
            <p>
                Moduliai:
            </p>
           
        )
    }
}

export default ObjectList
