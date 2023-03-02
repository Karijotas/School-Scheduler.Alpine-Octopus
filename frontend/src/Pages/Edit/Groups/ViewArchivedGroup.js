import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Button, Divider, Grid, Icon, Input, Message, Segment, Select, Table } from 'semantic-ui-react';
import { YEAR_OPTIONS } from '../../../Components/const';
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};

export function ViewArchivedGroup() {

    const [groups, setGroups] = useState([]);

    const params = useParams();

    const [active, setActive] = useState(true);

    const [programs, setPrograms] = useState([]);

    const [shifts, setShifts] = useState([]);

    const [error, setError] = useState();

    const [programId, setProgramId] = useState();

    const [shiftId, setShiftId] = useState();

    const [name, setName] = useState('');
    const [studentAmount, setstudentAmount] = useState('');

 
    useEffect(() => {
        fetch('/api/v1/groups/' + params.id)
            .then(response => response.json())
            .then(setGroups)
            .then(console.log(groups))
    }, [active, params]);

    const applyResult = () => {
        setActive(true)
    }

    const restoreGroup = (id) => {
        fetch("/api/v1/groups/restore/" + id, {
          method: "PATCH",
        })
        .then(applyResult)
      };

    useEffect(() => {
        fetch('/api/v1/programs/')
            .then((response) => response.json())
            .then((data) =>
                setPrograms(
                    data.map((x) => {
                        return { key: x.id, text: x.name, value: x.id };
                    })
                )
            )
    }, []);

    useEffect(() => {
        fetch('/api/v1/shifts')
            .then((response) => response.json())
            .then((data) => setShifts(
                data.map((x) => {
                    return { key: x.id, text: x.name, value: x.id };
                })
            )
            )
    }, []);

    return (


        <div>
            <MainMenu />
            <Grid columns={2}>
                <Grid.Column width={2} id='main'>
                    <EditMenu active='groups' />
                </Grid.Column>
                <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
                    <Segment id='segment' color='teal'>
                        {active && (<div >                            

                            <Table celled >
                                <Table.Header >
                                    <Table.Row  >
                                        <Table.HeaderCell >Grupės pavadinimas "Teams"</Table.HeaderCell>
                                        <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                                        <Table.HeaderCell>Suarchyvavimo data</Table.HeaderCell>
                                        <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                                    </Table.Row>
                                </Table.Header>
                                <Table.Body>
                                    <Table.Row  >
                                        <Table.Cell width={6} disabled>{groups.name}</Table.Cell>
                                        <Table.Cell disabled>{groups.schoolYear}</Table.Cell>
                                        <Table.Cell width={3} disabled> {groups.modifiedDate}  </Table.Cell>
                                        <Table.Cell collapsing> <Button id='details' className='controls' onClick={() => restoreGroup(params.id)}>Atstatyti</Button> </Table.Cell>
                                    </Table.Row>

                                </ Table.Body ></Table>
                            <Divider horizontal hidden>
                            </Divider>
                            <Table celled >
                                <Table.Header >
                                    <Table.Row  >
                                        <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                                        <Table.HeaderCell>Programa</Table.HeaderCell>
                                        <Table.HeaderCell>Pamaina</Table.HeaderCell>
                                    </Table.Row>
                                </Table.Header>

                                <Table.Body>
                                    <Table.Row  >
                                        <Table.Cell width={6} disabled>{groups.studentAmount}</Table.Cell>
                                        <Table.Cell disabled>{groups.program.name} </Table.Cell>
                                        <Table.Cell width={4} disabled>{groups.shift.name} </Table.Cell>
                                    </Table.Row>
                                </ Table.Body >
                            </Table>
                            <Button icon labelPosition="left" href='#/view/archives/groups'><Icon name="arrow left" />Atgal</Button>
                        </div>
                        )}  

                    </Segment>
                </Grid.Column>
            </Grid>
        </div >
    )
}