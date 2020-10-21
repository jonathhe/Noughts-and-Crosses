describe("Tests", () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000')
    })
    it("Test click on click on square", () => {
        cy.get(".SquareInput:first").click().should("have.value", "X")
    })
    it("Test reset", () => {
        cy.get(".SquareInput:first").click()
        cy.get("#resetButton").click()
        cy.get(".SquareInput:first").not("[value='X']")
    })
    it("Test namefield", () => {
        cy.get(".App div:first div:first input:first").type("testname")
    })
    
})