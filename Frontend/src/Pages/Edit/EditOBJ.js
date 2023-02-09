import React from 'react'
import { Button, Table} from 'semantic-ui-react'
import { SubjectList } from './EditPages/SubjectList'

export function EditOBJ() {
    // const [open, setOpen] = React.useState(false)

    return (
        
 <Table celled>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>ID</Table.HeaderCell>
                    <Table.HeaderCell>Pavadinimas</Table.HeaderCell>                    
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
            
                <Table.Row >
                        <Table.Cell collapsing>Hello</Table.Cell>
                        <Table.Cell><p>123</p></Table.Cell>
                        <Table.Cell collapsing> HELLOOOO
                        <Button><p>hello</p><SubjectList /></Button>
                            {/* <EditObjectModal /> */}
                        </Table.Cell>
                </Table.Row>
            </Table.Body>
        </Table>
        
    )
}