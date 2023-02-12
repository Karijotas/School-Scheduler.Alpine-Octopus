import React from 'react'
import { Table } from 'semantic-ui-react'
import EditObjectModal from '../EditObject'
function ViewGroups() {



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
    </div>
    )
}

export default ViewGroups