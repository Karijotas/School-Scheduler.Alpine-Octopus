import React, { useEffect, useState } from 'react'
import { Button, ButtonGroup, Confirm, Divider, Icon, Input, Pagination, Table } from 'semantic-ui-react'
import { CreateGroupPage } from '../../Create/CreateGroupPage';
import { EditSubjectObject } from './EditSubjectObject';
import './ViewGroups.css';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewSubjects() {

    const [active, setActive] = useState()
    const [create, setCreate] = useState('')
    const [nameText, setNameText] = useState("");
    const [subjects, setSubjects] = useState([]);
    
    const [subjectsforPaging, setSubjectsForPaging] = useState([]);
  
    const [activePage, setActivePage] = useState(0);
    const [pagecount, setPageCount] = useState()
  
  
  
    const fetchFilterSubjects = async () => {
      fetch(`/api/v1/subjects/page/name-filter/${nameText}?page=` + activePage)
        .then((response) => response.json())
        .then((jsonRespone) => setSubjects(jsonRespone));
    };
  
    const fetchSingleSubjects = () => {
      fetch('/api/v1/subjects')
        .then(response => response.json())
        .then(jsonResponse => setSubjectsForPaging(jsonResponse)).then(setPageCount(Math.ceil(subjectsforPaging.length / 10)))
    };
  
  
    const fetchSubjects = async () => {
      fetch(`/api/v1/subjects/page?page=` + activePage)
        .then((response) => response.json())
        .then((jsonRespones) => setSubjects(jsonRespones));
    };
  
    const removeSubject = (id) => {
      fetch("/api/v1/subjects/" + id, {
        method: "DELETE",
        headers: JSON_HEADERS,
      }).then(fetchSubjects);
    };
  
  
    useEffect(() => {
      nameText.length > 0 ? fetchFilterSubjects() : fetchSubjects();
    }, [activePage, nameText]);
  
    const [open, setOpen] = useState(false)
    const [close, setClose] = useState(false)
  
  
    useEffect(() => {
      if (pagecount !== null) {
        fetchSingleSubjects();
      }
    }, [subjects])

    return (


        <div>
            {create && (<div>
                <CreateGroupPage /></div>)}
            {active && (<div className='edit'>
                <EditSubjectObject id={active} /></div>)}


            {!active && !create && (

                <div id='subjects'>

                    <Input className='controls1' placeholder='Filtruoti pagal pavadinimą' value={nameText} onChange={(e) => setNameText(e.target.value)} />

                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują dalyką</Button>
                    <Divider horizontal hidden></Divider>

                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {subjects.map(subject => (

                                <Table.Row key={subject.id}>
                                    <Table.Cell>{subject.name}</Table.Cell>
                                    <Table.Cell>{subject.subjectModules}</Table.Cell>
                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(subject.id)}></Button>
                                        <Button basic color='black' compact title='Ištrinti' icon='trash alternate' onClick={() => setOpen(subject.id)}></Button>

                                        <Confirm
                                            open={open}
                                            header='Dėmesio!'
                                            content='Ar tikrai norite ištrinti?'
                                            cancelButton='Grįžti atgal'
                                            confirmButton="Ištrinti"
                                            onCancel={() => setOpen(false)}
                                            onConfirm={() => removeSubject(open)}
                                            size='small'
                                        />
                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>

                    </Table>
                    <Divider hidden></Divider>

                    <ButtonGroup basic compact>
                        <Button onClick={() => setActivePage(activePage <= 0 ? activePage : activePage - 1)} icon><Icon name="arrow left" />  </Button>
                        {[...Array(pagecount)].map((e, i) => {
                            return <Button key={i} onClick={() => setActivePage(i)}>{i + 1}</Button>
                        })}
                        <Button onClick={() => setActivePage(activePage >= pagecount - 1 ? activePage : activePage + 1)} icon><Icon name="arrow right" />  </Button>
                    </ButtonGroup>


                </div>
            )}


        </div>
    )
}
