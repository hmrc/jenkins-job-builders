package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters


class NodeParameter implements Parameter {

    private final String name
    private final List<String> nodes
    private final String triggerType

    private NodeParameter(String name, List<String> nodes, String triggerType) {
        this.name = name
        this.nodes = nodes
        this.triggerType = triggerType
    }

    static NodeParameter nodeParameter(String name, List<String> nodes, String triggerType) {
        new NodeParameter(name, nodes, triggerType)
    }

    @Override
    Closure toDsl() {
        return {
            nodeParam(name) {
                description(name)
                defaultNodes(nodes)
                allowedNodes(nodes)
                trigger(this.triggerType)
                eligibility('IgnoreOfflineNodeEligibility')
            }
        }
    }
}