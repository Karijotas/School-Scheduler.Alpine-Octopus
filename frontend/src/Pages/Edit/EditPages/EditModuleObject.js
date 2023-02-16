import React, { useEffect, useState } from 'react'
import { Button,  Icon,  Input, Table } from 'semantic-ui-react';
import { ViewModules } from './ViewModules';



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function EditModuleObject(props) {


    const [hide, setHide] = useState(false)

    const [active, setActive] = useState(true)
    

    const [error, setError] = useState();


    const [modules, setModules] = useState({
        name: '',
        modulesSubjects: '',
        description: '',
        createdDate: '',
        modifiedDate: '',
    });

    useEffect(() => {
        fetch('/api/v1/modules/' + props.id)
            .then(response => response.json())
            .then(setModules);
    }, [props]);




    const applyResult = () => {

        setHide(true)

    }

    const updateModules = () => {
        fetch('/api/v1/modules/' + props.id, {
            method: 'PATCH',
            headers: JSON_HEADERS,
            body: JSON.stringify(modules)
        }).then(result => {
            if (!result.ok) {
                setError('Update failed');
            } else {
                setError();
            }
        }).then(applyResult)
    };

    const updateProperty = (property, event) => {
        setModules({
            ...modules,
            [property]: event.target.value
        });
    };

    const editThis = () => {
        setActive(false);
    }
    // const removeGroup = (id) => {
    //     fetch('/api/v1/groups/' + params.id, {
    //         method: 'DELETE',
    //         headers: JSON_HEADERS
    //     })
    //     .then(() => window.location = listUrl);
    // }


    return (<div>{active && !hide &&(<div >

        <Table celled color='violet'>
            <Table.Header >
                <Table.Row  >
                    <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Modulio dalykai</Table.HeaderCell>
                    <Table.HeaderCell>Aprašymas</Table.HeaderCell>                   
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
                <Table.Row  >
                    <Table.Cell >{modules.name}</Table.Cell>
                    <Table.Cell >{modules.modulesSubjects}</Table.Cell>
                    <Table.Cell >{modules.description}</Table.Cell>
                    <Table.Cell collapsing > {modules.modifiedDate}  </Table.Cell>
                    <Table.Cell collapsing ><Button onClick={editThis}>Redaguoti</Button>
                    </Table.Cell>


                </Table.Row>

            </ Table.Body >
        </Table>
        <Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal</Button>

    </div>


    )}
        {!active && !hide && (<div >

            <Table celled color='violet'>
                <Table.Header >
                    <Table.Row  >
                    <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Modulio dalykai</Table.HeaderCell>
                    <Table.HeaderCell>Aprašymas</Table.HeaderCell>                   
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                    </Table.Row>
                </Table.Header>

                <Table.Body>
                    <Table.Row  >
                        <Table.Cell collapsing><Input value={modules.name} onChange={(e) => updateProperty('name', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input value={modules.modulesSubjects} onChange={(e) => updateProperty('modulesSubjects', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input placeholder={modules.description} /*options={yearOptions} value={groups.schoolYear} */onChange={(e) => updateProperty('description', e)} />
                        </Table.Cell>
                        {/* <Table.Cell collapsing><Input value={groups.studentAmount} onChange={(e) => updateProperty('studentAmount', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.program.id} value={groups.program} onChange={(e) => updateProperty('program', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.shift} value={groups.shift} onChange={(e) => updateProperty('shift', e)} /> */}
                        {/* </Table.Cell> */}

                        <Table.Cell collapsing> {modules.modifiedDate}  </Table.Cell>

                        <Table.Cell collapsing ><Button onClick={() => setActive(true)}>Atšaukti</Button><Button primary onClick={updateModules}>Atnaujinti</Button></Table.Cell>


                    </Table.Row>

                </ Table.Body >
            </Table>
            <Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal į sarašą</Button>

        </div>)}

        {hide && <div><ViewModules /></div>}



    </div>
    )
}

