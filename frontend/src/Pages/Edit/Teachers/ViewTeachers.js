import React, { useEffect, useState } from 'react';
import { Button, Confirm, Divider, Icon, Input, Table, Grid, Segment, ButtonGroup } from 'semantic-ui-react';
import MainMenu from '../../../Components/MainMenu';
import { NavLink } from 'react-router-dom';
import { EditMenu } from '../../../Components/EditMenu';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewTeachers() {
    // const yearOptions = [
    //     { key: 23, value: 2023, text: '2023' },
    //     { key: 24, value: 2024, text: '2024' },
    //     { key: 25, value: 2025, text: '2025' },
    //     { key: 26, value: 2026, text: '2026' },
    //     { key: 27, value: 2027, text: '2027' },
    //     { key: 28, value: 2028, text: '2028' },
    // ]

    const [active, setActive] = useState()

    const [create, setCreate] = useState('')

    const [teacher, setTeacher] = useState([]);
    const [teacherforPaging, setTeacherForPaging] = useState([]);


    const [nameText, setNameText] = useState('')

    const [yearText, setYearText] = useState('');

    const [programText, setProgramText] = useState('');

    const [activePage, setActivePage] = useState(0)

    const [pagecount, setPageCount] = useState()




    // const fetchProgramteacher = async () => {
    //     fetch('/api/v1/teacher/program-filter/' + programText)
    //         .then(response => response.json())
    //         .then(jsonResponse => setTeacher(jsonResponse));
    // };
    // const fetchYearteacher = async () => {
    //     fetch('/api/v1/teacher/year-filter/' + yearText)
    //         .then(response => response.json())
    //         .then(jsonResponse => setTeacher(jsonResponse));
    // };


    // const fetchFilterteacher = async () => {
    //     fetch('/api/v1/teacher/name-filter/' + nameText)
    //         .then(response => response.json())
    //         .then(jsonResponse => setTeacher(jsonResponse));
    // };

    // const fetchteacher = async () => {
    //     fetch('/api/v1/teachers/page?page=' + activePage)
    //         .then(response => response.json())
    //         .then(jsonResponse => setTeacher(jsonResponse));
    // };

    // const fetchSingleteacher = async () => {
    //     fetch('/api/v1/teachers/')
    //         .then(response => response.json())
    //         .then(jsonResponse => setTeacherForPaging(jsonResponse))
    //         .then(setPageCount(Math.ceil(teacherforPaging.length / 10)))
    //     // .then(console.log('pages:' + pagecount));
    // };



    // const removeTeacher = (id) => {
    //     fetch('/api/v1/teacher/' + id, {
    //         method: 'DELETE',
    //         headers: JSON_HEADERS
    //     })
    //     // .then(fetchteacher)
    //     //     .then(setOpen(false));
    // }



    // useEffect(() => {
    //    fetchSingleteacher();
    // }, []);

    // useEffect(() => {
    //     if (pagecount !== null) {
    //         fetchSingleteacher();
    //     }
    // }, [teacher])

    // const [open, setOpen] = useState(false)



    return (


        <div>
            <MainMenu />

            <Grid columns={2} >
                <Grid.Column width={2} id='main'>
                    <EditMenu />
                </Grid.Column>

                <Grid.Column textAlign='left' verticalAlign='top' width={13}>
                    <Segment id='segment' raised color='teal'>
                        {/* {create && (<div>
                <CreateteacherPage /></div>)}
            {active && (<div className='edit'>
                <EditteacherObject id={active} /></div>)} */}


                        {!active && !create && (

                            <div id='teacher'>

                                <Input className='controls1' placeholder='Filtruoti pagal vardą' value={nameText} onChange={(e) => setNameText(e.target.value)} />

                                {/* <Input className='controls1' placeholder='Filtruoti pagal dalykus' value={yearText} onChange={(e) => setYearText(e.target.value)} /> */}

                                <Input placeholder='Filtruoti pagal pamainas' value={programText} onChange={(e) => setProgramText(e.target.value)} />

                                <Button icon labelPosition='left' primary className='controls' as={NavLink} exact to='/create/teachers'><Icon name='database' />Kurti naują mokytojo įrašą</Button>
                                <Divider horizontal hidden></Divider>

                                <Table selectable >
                                    <Table.Header>
                                        <Table.Row>
                                            <Table.HeaderCell>Mokytojo vardas</Table.HeaderCell>
                                            <Table.HeaderCell>Teams vartotojo vardas</Table.HeaderCell>
                                            <Table.HeaderCell>Kontaktinis email (optional)</Table.HeaderCell>
                                            <Table.HeaderCell>Kontaktinis telefonas (optional)</Table.HeaderCell>
                                            <Table.HeaderCell>Valandos per savaitę</Table.HeaderCell>
                                            <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                                        </Table.Row>
                                    </Table.Header>

                                    <Table.Body>
                                        {/* {teacher.map(teacher => (

                                <Table.Row key={teacher.id}>
                                    <Table.Cell>{teacher.name + " " + teacher.surname}</Table.Cell>
                                    <Table.Cell>{teacher.loginEmail}</Table.Cell>
                                    <Table.Cell>{teacher.contactEmail}</Table.Cell>
                                    <Table.Cell>{teacher.phone}</Table.Cell>
                                    <Table.Cell>{teacher.workHoursPerWeek}</Table.Cell>
                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(teacher.id)}></Button>
                                        <Button basic color='black' compact title='Ištrinti' icon='trash alternate' onClick={() => setOpen(teacher.id)}></Button>

                                        <Confirm
                                            open={open}
                                            header='Dėmesio!'
                                            content='Ar tikrai norite ištrinti?'
                                            cancelButton='Grįžti atgal'
                                            confirmButton="Ištrinti"
                                            onCancel={() => setOpen(false)}
                                            onConfirm={() => removeTeacher(open)}
                                            size='small'
                                        />
                                    </Table.Cell>
                                </Table.Row>
                            ))} */}
                                    </Table.Body>

                                </Table>
                                <Divider hidden></Divider>

                                <ButtonGroup compact basic>
                                    <Button title='Atgal' onClick={() => setActivePage(activePage <= 0 ? activePage : activePage - 1)} icon><Icon name="arrow left" />  </Button>
                                    {[...Array(pagecount)].map((e, i) => {
                                        return <Button title={i + 1} key={i} active={activePage === i ? true : false} onClick={() => setActivePage(i)}>{i + 1}</Button>
                                    })}
                                    <Button title='Pirmyn' onClick={() => setActivePage(activePage >= pagecount - 1 ? activePage : activePage + 1)} icon><Icon name="arrow right" />  </Button>
                                </ButtonGroup>


                            </div>
                        )}
                    </Segment>
                </Grid.Column>

            </Grid>

        </div>
    )
}