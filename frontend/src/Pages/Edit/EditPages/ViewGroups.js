import React, { useEffect, useState } from 'react'
import { act } from 'react-dom/test-utils';
import { Button, Confirm, Divider, Header, Icon, Input, Label, Rail, Segment, Table } from 'semantic-ui-react'
import { CreatePage } from '../../Create/CreatePage';
import { EditGroupObject } from './EditGroupObject';
import './ViewGroups.css';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewGroups() {
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

    const [groups, setGroups] = useState([]);

    const [nameText, setNameText] = useState('')

    const [yearText, setYearText] = useState();

    const [programText, setProgramText] = useState();

    const [activePage, setActivePage] = useState(0)

    const [id, SetId] = useState('')


    const fetchProgramGroups = async () => {
        fetch('/api/v1/groups/program-filter/' + programText)
            .then(response => response.json())
            .then(jsonResponse => setGroups(jsonResponse));
    };
    const fetchYearGroups = async () => {
        fetch('/api/v1/groups/year-filter/' + yearText)
            .then(response => response.json())
            .then(jsonResponse => setGroups(jsonResponse));
    };




    const fetchFilterGroups = async () => {
        fetch('/api/v1/groups/name-filter/' + nameText)
            .then(response => response.json())
            .then(jsonResponse => setGroups(jsonResponse));
    };

    const fetchGroups = async () => {
        fetch('/api/v1/groups/page?page=' + activePage)
            .then(response => response.json())
            .then(jsonResponse => setGroups(jsonResponse));
    };

    const fetchSingleGroups = async () => {
        fetch('/api/v1/groups/')
            .then(response => response.json())
            .then(jsonResponse => setGroups(jsonResponse));
    };

    const removeGroup = (id) => {
        fetch('/api/v1/groups/' + id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        }).then(fetchGroups)
            .then(setOpen(false));
    }


    useEffect(() => {
        fetchGroups();
    }, [nameText, yearText, programText, activePage]);

    const [open, setOpen] = useState(false)
    const [close, setClose] = useState(false)


    return (


        <div>
            {create && (<div>
                <CreatePage /></div>)}
            {active && (<div className='edit'>
                <EditGroupObject id={active} /></div>)}


            {!active && !create && (

                <div id='groups'>

                    <Input className='controls1' placeholder='Filtruoti pagal pavadinimą' value={nameText} onChange={(e) => setNameText(e.target.value)} />

                    <Input className='controls1' placeholder='Filtruoti pagal mokslo metus' value={yearText} onChange={(e) => setYearText(e.target.value)} />

                    <Input placeholder='Filtruoti pagal programą' value={programText} onChange={(e) => setProgramText(e.target.value)} />





                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują grupę</Button>
                    <Divider horizontal hidden></Divider>

                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Grupės pavadinimas "Teams"</Table.HeaderCell>
                                <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                                <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                                <Table.HeaderCell>Programa</Table.HeaderCell>
                                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {groups.map(group => (

                                <Table.Row key={group.id}>
                                    <Table.Cell>{group.name}</Table.Cell>
                                    <Table.Cell>{group.schoolYear}</Table.Cell>
                                    <Table.Cell>{group.studentAmount}</Table.Cell>
                                    <Table.Cell>{group.programName}</Table.Cell>
                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(group.id)}></Button>
                                        <Button basic color='black' compact title='Ištrinti' icon='trash alternate' onClick={() => setOpen(true) && SetId(group.id)}></Button>
                                        <Confirm
                                            open={open}
                                            header='Dėmesio!'
                                            content='Ar tikrai norite ištrinti?'
                                            cancelButton='Grįžti atgal'
                                            confirmButton="Ištrinti"
                                            onCancel={() => setOpen(false)}
                                            onConfirm={() => removeGroup(id)}
                                            size='small'
                                        />
                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>

                    </Table>
                    <Divider hidden></Divider>

                    <Button onClick={() => setActivePage(0)}>1</Button>
                    <Button onClick={() => setActivePage(1)}>2</Button>
                </div>
            )}


        </div>
    )
}


