import {Navbar, Nav, Container} from "react-bootstrap";

const TopNavBar = () => {
    return (
        <>
            <Navbar collapseOnSelect fixed='top' expand='sm' bg='dark' variant={'dark'} id={'topNavBar'}>
                <Container>
                    <Nav>
                        {/*Aligned to tha right*/}
                        <div id={'user_details_div'}>
                            <Nav.Link href={'/'}>GraphList</Nav.Link>
                            <Nav.Link href={'/login'}>Login</Nav.Link>
                        </div>

                        {/*Aligned to tha left*/}
                        <div id={'search_bar_div'}>
                            <div class='search_bar' id='search_bar_div'>
                                <form
                                    className='search-form'
                                    id='input_keyword_form'
                                    name="form-keyword"
                                    onSubmit={e => {
                                        e.preventDefault();
                                    }}
                                    method='get'
                                >
                                    <label htmlFor="header-search">
                                        {/*<span className="visually-hidden">Search blog posts</span>*/}
                                    </label>
                                    <input
                                        type="text"
                                        id="header-search"
                                        placeholder="Enter Search keyword"
                                        name="input-keyword"
                                    />
                                    <button
                                        id="search_btn"
                                        type="submit"
                                        // onClick={this.updateAllGraphs}
                                    >Search
                                    </button>
                                </form>
                            </div>
                        </div>
                    </Nav>
                </Container>
            </Navbar>
        </>
    );
}

export default TopNavBar;