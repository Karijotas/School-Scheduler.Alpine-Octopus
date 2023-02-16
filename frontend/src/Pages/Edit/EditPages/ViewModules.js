import React, { useEffect, useState } from 'react'
import { Button, ButtonGroup, Confirm, Divider, Icon, Input, Table } from 'semantic-ui-react'
import { EditModuleObject } from './EditModuleObject';
import { CreateModulePage } from '../../Create/CreateModulePage';
import './ViewGroups.css';

const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewModules() {

    const [active, setActive] = useState()
    const [create, setCreate] = useState('')
    const [nameText, setNameText] = useState("");
    const [modules, setModules] = useState([]);
    
    const [modulesforPaging, setModulesForPaging] = useState([]);
  
    const [activePage, setActivePage] = useState(0);
    const [pagecount, setPageCount] = useState()
  
  
  
    const fetchFilterModules = async () => {
      fetch(`/api/v1/modules/page/name-filter/${nameText}?page=` + activePage)
        .then((response) => response.json())
        .then((jsonRespone) => setModules(jsonRespone));
    };
  
    const fetchSingleModules = () => {
      fetch('/api/v1/modules')
        .then(response => response.json())
        .then(jsonResponse => setModulesForPaging(jsonResponse)).then(setPageCount(Math.ceil(modulesforPaging.length / 10)))
    };
  
  
    const fetchModules = async () => {
      fetch(`/api/v1/modules/page?page=` + activePage)
        .then((response) => response.json())
        .then((jsonRespones) => setModules(jsonRespones));
    };
  
    const removeModule = (id) => {
      fetch("/api/v1/modules/" + id, {
        method: "DELETE",
        headers: JSON_HEADERS,
      }).then(fetchModules);
    };
  
  
    useEffect(() => {
      nameText.length > 0 ? fetchFilterModules() : fetchModules();
    }, [activePage, nameText]);
  
    const [open, setOpen] = useState(false)
    const [close, setClose] = useState(false)
  
  
    useEffect(() => {
      if (pagecount !== null) {
        fetchSingleModules();
      }
    }, [modules])

    return (


        <div>
            {create && (<div>
                <CreateModulePage /></div>)}
            {active && (<div className='edit'>
                <EditModuleObject id={active} /></div>)}


            {!active && !create && (

                <div id='modules'>

                    <Input className='controls1' placeholder='Filtruoti pagal pavadinimą' value={nameText} onChange={(e) => setNameText(e.target.value)} />

                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują modulį</Button>
                    <Divider horizontal hidden></Divider>

                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {modules.map(module => (

                                <Table.Row key={module.id}>
                                    <Table.Cell>{module.name}</Table.Cell>
                                    <Table.Cell>{module.modulesSubjects}</Table.Cell>
                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(module.id)}></Button>
                                        <Button basic color='black' compact title='Ištrinti' icon='trash alternate' onClick={() => setOpen(module.id)}></Button>

                                        <Confirm
                                            open={open}
                                            header='Dėmesio!'
                                            content='Ar tikrai norite ištrinti?'
                                            cancelButton='Grįžti atgal'
                                            confirmButton="Ištrinti"
                                            onCancel={() => setOpen(false)}
                                            onConfirm={() => removeModule(open)}
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
