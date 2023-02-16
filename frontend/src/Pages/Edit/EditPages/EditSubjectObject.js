import React, { useEffect, useState } from 'react'
import { Button,  Icon,  Input, Table } from 'semantic-ui-react';
import { ViewSubjects } from './ViewSubjects';



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function EditSubjectObject(props) {


    const [hide, setHide] = useState(false)

    const [active, setActive] = useState(true)
    

    const [error, setError] = useState();


    const [subjects, setSubjects] = useState({
        name: '',
        subjectModules: '',
        description: '',
        createdDate: '',
        modifiedDate: '',
    });

    useEffect(() => {
        fetch('/api/v1/subjects/' + props.id)
            .then(response => response.json())
            .then(setSubjects);
    }, [props]);




    const applyResult = () => {

        setHide(true)

    }

    const updateSubjects = () => {
        fetch('/api/v1/subjects/' + props.id, {
            method: 'PATCH',
            headers: JSON_HEADERS,
            body: JSON.stringify(subjects)
        }).then(result => {
            if (!result.ok) {
                setError('Update failed');
            } else {
                setError();
            }
        }).then(applyResult)
    };

    const updateProperty = (property, event) => {
        setSubjects({
            ...subjects,
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
                    <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Dalyko modulis/iai</Table.HeaderCell>
                    <Table.HeaderCell>Aprašymas</Table.HeaderCell>                   
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
                <Table.Row  >
                    <Table.Cell >{subjects.name}</Table.Cell>
                    <Table.Cell >{subjects.subjectModules}</Table.Cell>
                    <Table.Cell >{subjects.description}</Table.Cell>
                    <Table.Cell collapsing > {subjects.modifiedDate}  </Table.Cell>
                    <Table.Cell collapsing ><Button onClick={editThis}>Taisyti</Button>
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
                    <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Dalyko modulis/iai</Table.HeaderCell>
                    <Table.HeaderCell>Aprašymas</Table.HeaderCell>                   
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                    </Table.Row>
                </Table.Header>

                <Table.Body>
                    <Table.Row  >
                        <Table.Cell collapsing><Input value={subjects.name} onChange={(e) => updateProperty('name', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input value={subjects.subjectModules} onChange={(e) => updateProperty('subjectModules', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input placeholder={subjects.description} /*options={yearOptions} value={groups.schoolYear} */onChange={(e) => updateProperty('description', e)} />
                        </Table.Cell>
                        {/* <Table.Cell collapsing><Input value={groups.studentAmount} onChange={(e) => updateProperty('studentAmount', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.program.id} value={groups.program} onChange={(e) => updateProperty('program', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.shift} value={groups.shift} onChange={(e) => updateProperty('shift', e)} /> */}
                        {/* </Table.Cell> */}

                        <Table.Cell collapsing> {subjects.modifiedDate}  </Table.Cell>

                        <Table.Cell collapsing ><Button primary onClick={updateSubjects}>Atnaujinti</Button></Table.Cell>


                    </Table.Row>

                </ Table.Body >
            </Table>
            <Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal</Button>

        </div>)}

        {hide && <div><ViewSubjects /></div>}



    </div>
    )
}

